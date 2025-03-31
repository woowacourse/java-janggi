package infrastructure.dao;

import application.persistence.DbConnector;
import infrastructure.entity.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDao {

    private final DbConnector dbConnector;

    public GameDao(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public GameEntity findTurn() {
        String query = "SELECT current_turn FROM game";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new GameEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("current_turn")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(GameEntity gameEntity) {
        String query = "UPDATE turn SET current_turn = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameEntity.getCurrentTurn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(GameEntity gameEntity) {
        String query = "INSERT INTO game (name, current_turn) VALUES(?, ?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, gameEntity.getName());
            preparedStatement.setString(2, gameEntity.getCurrentTurn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        String query = "DELETE FROM turn";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<GameEntity> findAll() {
        String query = "SELECT * FROM game";

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
            throw new RuntimeException(e);
        }
    }
}
