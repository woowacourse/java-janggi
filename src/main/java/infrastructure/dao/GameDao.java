package infrastructure.dao;

import infrastructure.entity.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDao {

    private static final String TABLE = "games";

    private final DbConnector dbConnector;

    public GameDao(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public void updateGame(GameEntity gameEntity) {
        String query = "UPDATE " + TABLE + " SET current_turn = ? WHERE id = " + gameEntity.getId();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameEntity.getCurrentTurn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 데이터 업데이트에 실패했습니다.", e);
        }
    }

    public GameEntity save(GameEntity gameEntity) {
        String query = "INSERT INTO " + TABLE + " (name, current_turn) VALUES(?, ?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     query,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, gameEntity.getName());
            preparedStatement.setString(2, gameEntity.getCurrentTurn());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return new GameEntity(
                    resultSet.getLong(1),
                    gameEntity.getName(),
                    gameEntity.getCurrentTurn()
            );
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 데이터 저장에 실패했습니다.", e);
        }
    }

    public void deleteGame(GameEntity gameEntity) {
        String query = "DELETE FROM " + TABLE + " WHERE id = " + gameEntity.getId();

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 데이터 삭제에 실패했습니다.", e);
        }
    }

    public List<GameEntity> findAll() {
        String query = "SELECT * FROM " + TABLE;

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<GameEntity> gameEntities = new ArrayList<>();
            while (resultSet.next()) {
                gameEntities.add(new GameEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("current_turn")
                ));
            }
            return gameEntities;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 게임 데이터 조회에 실패했습니다.", e);
        }
    }
}
