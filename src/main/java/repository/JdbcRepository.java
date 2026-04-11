package repository;

import domain.Board;
import domain.Camp;
import domain.PieceType;
import domain.Position;
import domain.pieces.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JdbcRepository {

    private final String URL = "jdbc:mysql://localhost:3306/janggi";
    private final String USER = "root";
    private final String PASSWORD = "1020";

    public long createGame(Board board, Camp camp) {
        String sql = "INSERT INTO game (game_status, current_camp, han_score, cho_score) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "playing");
            preparedStatement.setString(2, camp.name());
            preparedStatement.setDouble(3, board.calculateScoreByCamp(Camp.HAN));
            preparedStatement.setDouble(4, board.calculateScoreByCamp(Camp.CHO));
            preparedStatement.executeUpdate();

            try (java.sql.ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new RuntimeException("game id 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }

    public void updateBoard(Long gameId, Board board) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String deleteSql = "DELETE FROM board WHERE game_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setLong(1, gameId);
                preparedStatement.executeUpdate();
            }

            String sql = "INSERT INTO board (game_id, col_pos, row_pos, piece_type, camp) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                Map<Position, Piece> boardToStore = board.getBoard();
                for (Entry<Position, Piece> positionPieceEntry : boardToStore.entrySet()) {
                    Position position = positionPieceEntry.getKey();
                    Piece piece = positionPieceEntry.getValue();
                    preparedStatement.setLong(1, gameId);
                    preparedStatement.setInt(2, position.getCol());
                    preparedStatement.setInt(3, position.getRow());
                    preparedStatement.setString(4, piece.getPieceType().name());
                    preparedStatement.setString(5, piece.getCamp().name());

                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }

    public void updateGame(Long gameId, Board board, Camp camp, boolean finished) {
        String sql = "UPDATE game SET game_status=?, current_camp=?, han_score=?, cho_score=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, finished ? "finished" : "playing");
            preparedStatement.setString(2, camp.name());
            preparedStatement.setDouble(3, board.calculateScoreByCamp(Camp.HAN));
            preparedStatement.setDouble(4, board.calculateScoreByCamp(Camp.CHO));
            preparedStatement.setLong(5, gameId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }

    public Long findPlayingGame() {
        String sql = "SELECT id FROM game WHERE game_status=? ORDER BY id DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "playing");

            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }

    public Board findBoard(Long gameId) {
        String sql = "SELECT col_pos, row_pos, piece_type, camp FROM board WHERE game_id=?";
        try (Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);

            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Position, Piece> board = new HashMap<>();
                while (resultSet.next()) {
                    int col_pos = resultSet.getInt("col_pos");
                    int row_pos = resultSet.getInt("row_pos");
                    String pieceTypeStr = resultSet.getString("piece_type");
                    String campStr = resultSet.getString("camp");
                    Position position = new Position(col_pos, row_pos);
                    PieceType pieceType = PieceType.valueOf(pieceTypeStr);
                    Camp camp = Camp.valueOf(campStr);
                    Piece piece = Piece.of(pieceType, camp);

                    board.put(position, piece);
                }
                return Board.restore(board);
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }

    public Camp findCurrentCamp(Long gameId) {
        String sql = "SELECT current_camp FROM game WHERE id=?";
        try (Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);

            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String campStr = resultSet.getString("current_camp");
                    return Camp.valueOf(campStr);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }
}
