package janggi.repository;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import janggi.config.DatabaseManager;
import janggi.domain.board.Board;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.CurrentTurn;
import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {

    @Override
    public Long save(Game game) {
        String gameSql = "INSERT INTO janggi_game (turn) VALUES (?)";

        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement gameStatement = connection.prepareStatement(gameSql, RETURN_GENERATED_KEYS)) {
                gameStatement.setString(1, game.currentDynasty().name());
                gameStatement.executeUpdate();

                ResultSet keys = gameStatement.getGeneratedKeys();
                if (!keys.next()) {
                    throw new RuntimeException("id 생성 실패");
                }

                long gameId = keys.getLong(1);
                savePieces(connection, gameId, game);

                connection.commit();
                return gameId;
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("저장 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public Optional<Game> findById(Long gameId) {
        String gameSql = "SELECT turn FROM janggi_game WHERE id = ?";
        String pieceSql = "SELECT row_pos, col_pos, team, type FROM piece WHERE janggi_game_id = ?";

        try (Connection connection = DatabaseManager.getConnection()) {
            Map<Position, Piece> pieces = findPieces(connection, pieceSql, gameId);
            Board board = Board.restore(pieces);

            Dynasty currentTurn = findCurrentTurn(connection, gameSql, gameId);
            Game game = Game.restore(board, new CurrentTurn(currentTurn));

            return Optional.of(game);
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
    }

    @Override
    public void update(Long gameId, Game game) {
        String updateGameSql = "UPDATE janggi_game SET turn = ? WHERE id = ?";
        String deletePieceSql = "DELETE FROM piece WHERE janggi_game_id = ?";

        try (Connection connection = DatabaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (
                    PreparedStatement updateGameStatement = connection.prepareStatement(updateGameSql);
                    PreparedStatement deletePieceStatement = connection.prepareStatement(deletePieceSql)
            ) {
                updateGameStatement.setString(1, game.currentDynasty().name());
                updateGameStatement.setLong(2, gameId);
                updateGameStatement.executeUpdate();

                deletePieceStatement.setLong(1, gameId);
                deletePieceStatement.executeUpdate();

                savePieces(connection, gameId, game);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("수정 실패", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("수정 실패", e);
        }
    }

    @Override
    public Optional<Long> findRecentlyGameId() {
        String sql = "SELECT id FROM janggi_game ORDER BY id DESC LIMIT 1";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException("최근 게임 조회 실패", e);
        }
    }

    private Dynasty findCurrentTurn(Connection connection, String sql, Long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return Dynasty.valueOf(resultSet.getString("turn"));
            }
        }
    }

    private Map<Position, Piece> findPieces(Connection connection, String sql, Long gameId) throws SQLException {
        Map<Position, Piece> boardMap = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int row = resultSet.getInt("row_pos");
                    int column = resultSet.getInt("col_pos");
                    Dynasty dynasty = Dynasty.valueOf(resultSet.getString("team"));
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("type"));

                    Position position = Position.from(row, column);
                    Piece piece = new Piece(dynasty, pieceType);

                    boardMap.put(position, piece);
                }
            }
        }
        return boardMap;
    }

    private void savePieces(Connection connection, Long gameId, Game game) throws SQLException {
        String sql = "INSERT INTO piece (janggi_game_id, row_pos, col_pos, team, type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : game.boardMap().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                statement.setLong(1, gameId);
                statement.setInt(2, position.row().row());
                statement.setInt(3, position.column().column());
                statement.setString(4, piece.dynasty().name());
                statement.setString(5, piece.pieceType().name());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

}
