package infrastructure.dao;

import infrastructure.entity.GameEntity;
import infrastructure.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private static final String TABLE = "pieces";

    private final DbConnector dbConnector;

    public PieceDao(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void save(List<PieceEntity> pieceEntities, GameEntity gameEntity) {
        Long gameId = gameEntity.getId();
        String query =
                "INSERT INTO " + TABLE + " (piece_name, x, y, country, game_id) VALUES(?, ?, ?, ?, " + gameId + ")";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (PieceEntity pieceEntity : pieceEntities) {
                preparedStatement.setString(1, pieceEntity.getPieceName());
                preparedStatement.setInt(2, pieceEntity.getX());
                preparedStatement.setInt(3, pieceEntity.getY());
                preparedStatement.setString(4, pieceEntity.getCountry());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 데이터 저장에 실패했습니다.", e);
        }
    }

    public void deleteByGame(GameEntity gameEntity) {
        String query = "DELETE FROM " + TABLE + " WHERE game_id = " + gameEntity.getId();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 데이터 삭제에 실패했습니다.", e);
        }
    }

    public List<PieceEntity> findByGame(GameEntity gameEntity) {
        String query = "SELECT * FROM pieces WHERE game_id = " + gameEntity.getId();

        List<PieceEntity> pieceEntities = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pieceEntities.add(new PieceEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("piece_name"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getString("country")
                ));
            }
            return pieceEntities;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 데이터 조회에 실패했습니다.", e);
        }

    }
}
