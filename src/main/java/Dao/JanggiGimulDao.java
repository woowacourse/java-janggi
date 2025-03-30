package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import piece.Piece;
import pieceProperty.PieceType;
import pieceProperty.Position;
import player.JanggiPan;

public class JanggiGimulDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void updateAttackGimul(Position currentPosition, Position destination, int playerId) {
        var query = "UPDATE piece SET row_position = ?, col_position = ? WHERE row_position = ? AND col_position = ? AND player_id = ?";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, destination.getRow());
            preparedStatement.setInt(2, destination.getCol());
            preparedStatement.setInt(3, currentPosition.getRow());
            preparedStatement.setInt(4, currentPosition.getCol());
            preparedStatement.setInt(5, playerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDefenceGimul(Position destination, int playerId) {
        var query = "UPDATE piece SET is_alive = false WHERE row_position = ? AND col_position = ? AND player_id = ?";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, destination.getRow());
            preparedStatement.setInt(2, destination.getCol());
            preparedStatement.setInt(3, playerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertChoPieces(JanggiPan janggiPan, int playerId) {
        final var query = "INSERT INTO piece (player_id, type, row_position, col_position, is_alive) VALUES (?, ?, ?, ?, ?)";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            for (Map.Entry<Position, Piece> entry : janggiPan.getPieces().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setInt(1, playerId);
                preparedStatement.setString(2, piece.getPieceType().toString());
                preparedStatement.setInt(3, position.getRow());
                preparedStatement.setInt(4, position.getCol());
                preparedStatement.setBoolean(5, true);
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertHanPieces(JanggiPan janggiPan, int playerId) {
        final var query = "INSERT INTO piece (player_id, type, row_position, col_position, is_alive) VALUES (?, ?, ?, ?, ?)";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            for (Map.Entry<Position, Piece> entry : janggiPan.getPieces().entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setInt(1, playerId);
                preparedStatement.setString(2, piece.getPieceType().toString());
                preparedStatement.setInt(3, position.getRow());
                preparedStatement.setInt(4, position.getCol());
                preparedStatement.setBoolean(5, true);
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllPieces() {
        var query = "TRUNCATE TABLE piece";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, PieceType> findChoAllGimul() {
        Map<Position, PieceType> pieces = new HashMap<>();
        String query = "SELECT * FROM piece WHERE player_id = 2 AND is_alive = true";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int row = resultSet.getInt("row_position");
                    int col = resultSet.getInt("col_position");

                    PieceType pieceType = PieceType.getPieceTypeBy(resultSet.getString("type"));
                    pieces.put(new Position(row, col), pieceType);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생하였습니다.", e);
        }
        return pieces;
    }

    public Map<Position, PieceType> findHanAllGimul() {
        Map<Position, PieceType> pieces = new HashMap<>();
        String query = "SELECT * FROM piece WHERE player_id = 1 AND is_alive = true";
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int row = resultSet.getInt("row_position");
                    int col = resultSet.getInt("col_position");

                    PieceType pieceType = PieceType.getPieceTypeBy(resultSet.getString("type"));

                    pieces.put(new Position(row, col), pieceType);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생하였습니다.", e);
        }
        return pieces;
    }
}
