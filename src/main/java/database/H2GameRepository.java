package database;

import domain.board.Board;
import domain.board.PiecePosition;
import domain.board.Position;
import domain.game.Game;
import domain.game.GameRepository;
import domain.game.GameStatus;
import domain.game.TurnManager;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class H2GameRepository implements GameRepository {
    private static final String FIND_IN_PROGRESS_SQL = """
            SELECT id, status, current_turn
            FROM game_session
            WHERE status = ?
            ORDER BY id DESC
            LIMIT 1
            """;
    private static final String FIND_SNAPSHOTS_SQL = """
            SELECT row_number, column_number, team_color, piece_type
            FROM piece_snapshot
            WHERE session_id = ?
            """;
    private static final String INSERT_GAME_SQL = """
            INSERT INTO game_session(status, current_turn)
            VALUES (?, ?)
            """;
    private static final String UPDATE_GAME_SQL = """
            UPDATE game_session
            SET status = ?, current_turn = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;
    private static final String DELETE_SNAPSHOTS_SQL = "DELETE FROM piece_snapshot WHERE session_id = ?";
    private static final String INSERT_SNAPSHOT_SQL = """
            INSERT INTO piece_snapshot(session_id, row_number, column_number, team_color, piece_type)
            VALUES (?, ?, ?, ?, ?)
            """;
    private static final String DELETE_IN_PROGRESS_SQL = "DELETE FROM game_session WHERE status = ?";

    private final ConnectionManager connectionManager;

    public H2GameRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<Game> findInProgress() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_IN_PROGRESS_SQL)) {
            statement.setString(1, GameStatus.IN_PROGRESS.name());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }

            long id = resultSet.getLong("id");
            Board board = loadBoard(connection, id);
            TurnManager turnManager = new TurnManager(TeamColor.valueOf(resultSet.getString("current_turn")));
            Game game = new Game(
                    id,
                    board,
                    turnManager,
                    GameStatus.valueOf(resultSet.getString("status"))
            );
            return Optional.of(game);
        } catch (SQLException exception) {
            throw new IllegalStateException("진행 중 게임 조회에 실패했습니다.", exception);
        }
    }

    @Override
    public Game save(Game game) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                if (game.id() == null) {
                    insertGame(connection, game);
                } else {
                    updateGame(connection, game);
                }

                replaceSnapshots(connection, game);
                connection.commit();
                return game;
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", exception);
        }
    }

    @Override
    public void deleteInProgress() {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_IN_PROGRESS_SQL)) {
            statement.setString(1, GameStatus.IN_PROGRESS.name());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new IllegalStateException("진행 중 게임 삭제에 실패했습니다.", exception);
        }
    }

    private Board loadBoard(Connection connection, long sessionId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_SNAPSHOTS_SQL)) {
            statement.setLong(1, sessionId);
            ResultSet resultSet = statement.executeQuery();
            Map<Position, Piece> pieces = new HashMap<>();
            while (resultSet.next()) {
                Position position = Position.of(
                        resultSet.getInt("row_number"),
                        resultSet.getInt("column_number")
                );
                Piece piece = Piece.of(
                        TeamColor.valueOf(resultSet.getString("team_color")),
                        PieceType.valueOf(resultSet.getString("piece_type"))
                );
                pieces.put(position, piece);
            }
            return new Board(pieces);
        }
    }

    private void insertGame(Connection connection, Game game) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_SQL, Statement.RETURN_GENERATED_KEYS)) {
            bindGame(statement, game);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new IllegalStateException("게임 식별자를 생성하지 못했습니다.");
            }
            game.assignId(generatedKeys.getLong(1));
        }
    }

    private void updateGame(Connection connection, Game game) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_SQL)) {
            bindGame(statement, game);
            statement.setLong(3, game.id());
            statement.executeUpdate();
        }
    }

    private void bindGame(PreparedStatement statement, Game game) throws SQLException {
        statement.setString(1, game.status().name());
        statement.setString(2, game.currentTurn().name());
    }

    private void replaceSnapshots(Connection connection, Game game) throws SQLException {
        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_SNAPSHOTS_SQL)) {
            deleteStatement.setLong(1, game.id());
            deleteStatement.executeUpdate();
        }

        try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_SNAPSHOT_SQL)) {
            for (PiecePosition piecePosition : game.board().findAllPieces()) {
                insertStatement.setLong(1, game.id());
                insertStatement.setInt(2, piecePosition.position().row());
                insertStatement.setInt(3, piecePosition.position().column());
                insertStatement.setString(4, piecePosition.piece().getTeamColor().name());
                insertStatement.setString(5, piecePosition.piece().getPieceType().name());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }
}
