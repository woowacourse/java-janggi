package janggi.data.dao.mysql;

import janggi.board.point.Point;
import janggi.data.DatabaseConnection;
import janggi.data.dao.BoardDao;
import janggi.data.dao.CampDao;
import janggi.piece.Camp;
import janggi.piece.Piece;
import janggi.piece.PieceSymbol;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public final class MySqlBoardDao implements BoardDao {

    private final CampDao campDao;

    public MySqlBoardDao(CampDao campDao) {
        this.campDao = campDao;
    }

    @Override
    public void create() {
        final String query = """
                INSERT INTO board (is_end, turn_camp_id)
                VALUES (false, ?)
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, campDao.findIdByName(Camp.CHU.name()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endGame() {
        final String query = "UPDATE board SET is_end = 1 WHERE id = ?";
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, findCurrentBoardId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsActiveGame() {
        final String query = "SELECT COUNT(*) as count FROM board";
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next() && resultSet.getInt("count") > 0) {
                final String activeQuery = "SELECT is_end FROM board WHERE id = (SELECT MAX(id) FROM board)";
                try (final var activeStatement = connection.prepareStatement(activeQuery);
                     final var activeResult = activeStatement.executeQuery()) {
                    if (activeResult.next()) {
                        return !activeResult.getBoolean("is_end");
                    }
                }
            }
            return false;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findCurrentBoardId() {
        final String query = "SELECT MAX(id) AS current_id FROM board";
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("current_id");
            }
            throw new IllegalArgumentException("게임이 시작되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LocalDateTime findCurrentBoardCreatedAt() {
        final String query = "SELECT created_at FROM board WHERE id = ?";
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, findCurrentBoardId());
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getTimestamp("created_at")
                        .toInstant()
                        .atZone(ZoneId.of("Asia/Seoul"))
                        .toLocalDateTime();
            }
            throw new IllegalArgumentException("게임이 시작되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Camp findCurrentTurnCamp() {
        final String query = """
                SELECT c.name AS turn_camp_name
                FROM board 
                    JOIN camp c ON board.turn_camp_id = c.id
                WHERE board.id = ?
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, findCurrentBoardId());
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Camp.valueOf(resultSet.getString("turn_camp_name"));
            }
            throw new IllegalArgumentException("게임이 시작되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void turnChange() {
        final String query = """
                UPDATE board b
                JOIN camp c ON b.turn_camp_id = c.id
                SET b.turn_camp_id = (CASE WHEN c.name = 'CHU' THEN 
                    (SELECT id FROM camp WHERE name = 'HAN') 
                    ELSE 
                    (SELECT id FROM camp WHERE name = 'CHU') 
                END)
                WHERE b.id = ?;
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            int boardId = findCurrentBoardId();
            preparedStatement.setInt(1, boardId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Point, Piece> findCurrentBoardPieces() {
        final String query = """
                SELECT p.x, p.y, ps.name AS piece_name, c.name AS camp_name
                FROM piece p
                    JOIN piece_symbol ps ON p.piece_symbol_id = ps.id
                    JOIN camp c ON p.camp_id = c.id
                WHERE p.board_id = ?
                """;
        try (final var connection = DatabaseConnection.createConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, findCurrentBoardId());
            final var resultSet = preparedStatement.executeQuery();
            Map<Point, Piece> pieceMap = new HashMap<>();
            while (resultSet.next()) {
                Point point = new Point(resultSet.getInt("x"), resultSet.getInt("y"));
                Piece piece = createPiece(resultSet);
                pieceMap.put(point, piece);
            }
            return pieceMap;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece createPiece(ResultSet resultSet) throws SQLException {
        Camp camp = Camp.valueOf(resultSet.getString("camp_name"));
        PieceSymbol pieceSymbol = PieceSymbol.valueOf(resultSet.getString("piece_name"));
        return pieceSymbol.createPiece(camp);
    }
}
