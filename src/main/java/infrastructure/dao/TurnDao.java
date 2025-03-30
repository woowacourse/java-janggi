package infrastructure.dao;

import application.persistence.DbConnector;
import infrastructure.entity.TurnEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {

    private final DbConnector dbConnector;

    public TurnDao(DbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public TurnEntity findTurn() {
        String query = "SELECT current_turn FROM turn";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new TurnEntity(resultSet.getString("current_turn"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(TurnEntity turnEntity) {
        String query = "UPDATE turn SET current_turn = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnEntity.getCurrentTurn());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(TurnEntity turnEntity) {
        String query = "INSERT INTO turn (current_turn) VALUES(?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, turnEntity.getCurrentTurn());
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
}
