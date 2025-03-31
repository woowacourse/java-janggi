package repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import repository.connection.ConnectDatabase;
import repository.connection.ConnectMysql;
import repository.entity.TurnEntity;

public class TurnDao {

    public String findTurn() {
        final var query = "SELECT * FROM TURN";
        ConnectDatabase connectDatabase = new ConnectMysql();
        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("turn");
            }

            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void addTurn(TurnEntity turnEntity) {
        final var query = "INSERT INTO TURN (turn) VALUES(?)";

        ConnectDatabase connectDatabase = new ConnectMysql();
        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, turnEntity.turn());
            preparedStatement.executeUpdate();

            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(TurnEntity nextTurn) {
        final var query = "UPDATE TURN SET turn=(?)";

        ConnectDatabase connectDatabase = new ConnectMysql();
        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nextTurn.turn());
            preparedStatement.executeUpdate();

            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
