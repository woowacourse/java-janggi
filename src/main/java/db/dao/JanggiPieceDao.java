package db.dao;

import db.connection.DBConnection;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.PieceFactory;
import janggiGame.piece.Type;
import janggiGame.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JanggiPieceDao {
    private final DBConnection dbConnection;

    public JanggiPieceDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void saveAll(Long gameId, Map<Position, Piece> pieces) {
        String sql = "INSERT INTO janggi_piece (janggi_game_id, x, y, dynasty, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                preparedStatement.setLong(1, gameId);
                preparedStatement.setInt(2, position.getX());
                preparedStatement.setInt(3, position.getY());
                preparedStatement.setString(4, piece.getDynasty().name());
                preparedStatement.setString(5, piece.getType().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 상태 저장에 실패하였습니다. gameId: " + gameId);
        }
    }

    public Map<Position, Piece> findByGameId(Long gameId) {
        String sql = "SELECT * FROM janggi_piece WHERE janggi_game_id = ?";
        Map<Position, Piece> pieces = new HashMap<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    String dynasty = resultSet.getString("dynasty");
                    String type = resultSet.getString("type");

                    Position position = Position.getInstanceBy(x, y);
                    Piece piece = PieceFactory.createPieceOf(Type.valueOf(type), Dynasty.valueOf(dynasty));
                    pieces.put(position, piece);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 해당 게임에 속한 기물들의 조회에 실패하였습니다. gameId: " + gameId);
        }
        return pieces;
    }

    public void updatePiecePosition(Long gameId, Position origin, Position destination) {
        String sql = "UPDATE janggi_piece SET x = ?, y = ? WHERE janggi_game_id = ? AND x = ? AND y = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, destination.getX());
            preparedStatement.setInt(2, destination.getY());
            preparedStatement.setLong(3, gameId);
            preparedStatement.setInt(4, origin.getX());
            preparedStatement.setInt(5, origin.getY());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 위치 업데이트에 실패하였습니다. gameId: " + gameId);
        }
    }

    public void deletePieceAt(Long gameId, Position position) {
        String sql = "DELETE FROM janggi_piece WHERE janggi_game_id = ? AND x = ? AND y = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);
            preparedStatement.setInt(2, position.getX());
            preparedStatement.setInt(3, position.getY());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 삭제에 실패하였습니다. gameId: " + gameId);
        }
    }
}
