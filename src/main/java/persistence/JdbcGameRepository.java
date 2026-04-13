package persistence;

import domain.board.Board;
import domain.board.Formation;
import domain.common.Position;
import domain.common.Side;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcGameRepository {
    private static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    private static final String STATUS_FINISHED = "FINISHED";
    private static final String SIDE_CHO = Side.CHO.name();
    private static final String SIDE_HAN = Side.HAN.name();

    private final ConnectionFactory connectionFactory;

    public JdbcGameRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public long createGame(
            String choPlayerName,
            String hanPlayerName,
            Formation choFormation,
            Formation hanFormation,
            Map<Position, Piece> board,
            Side currentSide,
            int moveCount
    ) {
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long gameId = insertGame(connection, currentSide, moveCount);
                insertParticipants(connection, gameId, choPlayerName, hanPlayerName, choFormation, hanFormation);
                replacePieces(connection, gameId, board);
                connection.commit();
                return gameId;
            } catch (SQLException e) {
                rollback(connection, e);
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateGameState(long gameId, Map<Position, Piece> board, Side currentSide, int moveCount) {
        String updateGameSql = """
                UPDATE game
                SET current_side = ?, move_count = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement updateGame = connection.prepareStatement(updateGameSql)) {
                    updateGame.setString(1, currentSide.name());
                    updateGame.setInt(2, moveCount);
                    updateGame.setLong(3, gameId);
                    updateGame.executeUpdate();
                }
                replacePieces(connection, gameId, board);
                connection.commit();
            } catch (SQLException e) {
                rollback(connection, e);
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SavedGameSummary> findInProgressGames() {
        String sql = """
                SELECT g.id,
                       MAX(CASE WHEN gp.side = ? THEN gp.player_name END) AS cho_player_name,
                       MAX(CASE WHEN gp.side = ? THEN gp.player_name END) AS han_player_name,
                       g.move_count,
                       g.updated_at
                FROM game g
                JOIN game_participant gp ON gp.game_id = g.id
                WHERE g.status = ?
                GROUP BY g.id, g.move_count, g.updated_at
                ORDER BY g.updated_at DESC, g.id DESC
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, SIDE_CHO);
            preparedStatement.setString(2, SIDE_HAN);
            preparedStatement.setString(3, STATUS_IN_PROGRESS);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<SavedGameSummary> savedGames = new ArrayList<>();
                while (resultSet.next()) {
                    savedGames.add(new SavedGameSummary(
                            resultSet.getLong("id"),
                            resultSet.getString("cho_player_name"),
                            resultSet.getString("han_player_name"),
                            resultSet.getInt("move_count")
                    ));
                }
                return savedGames;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<SavedGame> findInProgressById(long gameId) {
        String gameSql = """
                SELECT id, current_side, move_count
                FROM game
                WHERE status = ? AND id = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(gameSql)) {
            preparedStatement.setString(1, STATUS_IN_PROGRESS);
            preparedStatement.setLong(2, gameId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                long foundGameId = resultSet.getLong("id");
                Side currentSide = Side.valueOf(resultSet.getString("current_side"));
                int moveCount = resultSet.getInt("move_count");
                ParticipantData participantData = findParticipantData(connection, foundGameId);
                Board board = findBoard(connection, foundGameId);

                return Optional.of(new SavedGame(
                        foundGameId,
                        participantData.choPlayerName(),
                        participantData.hanPlayerName(),
                        participantData.choFormation(),
                        participantData.hanFormation(),
                        board,
                        currentSide,
                        moveCount
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishGame(long gameId) {
        String sql = """
                UPDATE game
                SET status = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, STATUS_FINISHED);
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long insertGame(Connection connection, Side currentSide, int moveCount) throws SQLException {
        String sql = """
                INSERT INTO game(status, current_side, move_count)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, STATUS_IN_PROGRESS);
            preparedStatement.setString(2, currentSide.name());
            preparedStatement.setInt(3, moveCount);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new RuntimeException("게임 ID를 생성하지 못했습니다.");
                }
                return generatedKeys.getLong(1);
            }
        }
    }

    private void insertParticipants(
            Connection connection,
            long gameId,
            String choPlayerName,
            String hanPlayerName,
            Formation choFormation,
            Formation hanFormation
    ) throws SQLException {
        String sql = """
                INSERT INTO game_participant(game_id, side, player_name, formation)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, SIDE_CHO);
            preparedStatement.setString(3, choPlayerName);
            preparedStatement.setString(4, choFormation.name());
            preparedStatement.addBatch();

            preparedStatement.setLong(1, gameId);
            preparedStatement.setString(2, SIDE_HAN);
            preparedStatement.setString(3, hanPlayerName);
            preparedStatement.setString(4, hanFormation.name());
            preparedStatement.addBatch();

            preparedStatement.executeBatch();
        }
    }

    private void replacePieces(Connection connection, long gameId, Map<Position, Piece> board) throws SQLException {
        String deleteSql = "DELETE FROM game_piece WHERE game_id = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setLong(1, gameId);
            deleteStatement.executeUpdate();
        }

        String insertSql = """
                INSERT INTO game_piece(game_id, x, y, piece_side, piece_type)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                List<Integer> coordinates = position.getPosition();

                insertStatement.setLong(1, gameId);
                insertStatement.setInt(2, coordinates.getFirst());
                insertStatement.setInt(3, coordinates.getLast());
                insertStatement.setString(4, piece.getSide().name());
                insertStatement.setString(5, piece.getPieceType().name());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    private ParticipantData findParticipantData(Connection connection, long gameId) throws SQLException {
        String sql = """
                SELECT side, player_name, formation
                FROM game_participant
                WHERE game_id = ?
                """;

        String choPlayerName = null;
        String hanPlayerName = null;
        Formation choFormation = null;
        Formation hanFormation = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Side side = Side.valueOf(resultSet.getString("side"));
                    String playerName = resultSet.getString("player_name");
                    Formation formation = Formation.valueOf(resultSet.getString("formation"));

                    if (side == Side.CHO) {
                        choPlayerName = playerName;
                        choFormation = formation;
                    }
                    if (side == Side.HAN) {
                        hanPlayerName = playerName;
                        hanFormation = formation;
                    }
                }
            }
        }

        if (choPlayerName == null || hanPlayerName == null || choFormation == null || hanFormation == null) {
            throw new IllegalStateException("참가자 정보가 올바르지 않습니다. gameId=" + gameId);
        }
        return new ParticipantData(choPlayerName, hanPlayerName, choFormation, hanFormation);
    }

    private Board findBoard(Connection connection, long gameId) throws SQLException {
        String sql = """
                SELECT x, y, piece_side, piece_type
                FROM game_piece
                WHERE game_id = ?
                """;

        Map<Position, Piece> board = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Position position = Position.of(resultSet.getInt("x"), resultSet.getInt("y"));
                    Side side = Side.valueOf(resultSet.getString("piece_side"));
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    board.put(position, createPiece(side, pieceType));
                }
            }
        }
        return new Board(board);
    }

    private Piece createPiece(Side side, PieceType pieceType) {
        return switch (pieceType) {
            case GENERAL -> PieceFactory.createGeneral(side);
            case CHARIOT -> PieceFactory.createChariot(side);
            case CANNON -> PieceFactory.createCannon(side);
            case HORSE -> PieceFactory.createHorse(side);
            case ELEPHANT -> PieceFactory.createElephant(side);
            case GUARD -> PieceFactory.createGuard(side);
            case SOLDIER -> PieceFactory.createSoldier(side);
        };
    }

    private void rollback(Connection connection, SQLException exception) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            exception.addSuppressed(rollbackException);
        }
    }

    private record ParticipantData(
            String choPlayerName,
            String hanPlayerName,
            Formation choFormation,
            Formation hanFormation
    ) {
    }
}
