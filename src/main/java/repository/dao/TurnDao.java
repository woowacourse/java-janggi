package repository.dao;

import java.sql.SQLException;
import repository.connection.ConnectMysql;
import repository.entity.TurnEntity;

public class TurnDao {

    public String findTurn() {
        final var query = "SELECT * FROM TURN";
        try (final var connection = new ConnectMysql().create();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("turn");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void addTurn(TurnEntity turnEntity) {
        final var query = "INSERT INTO TURN (turn) VALUES(?)";
        try (final var connection = new ConnectMysql().create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, turnEntity.turn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(TurnEntity nextTurn) {
        final var query = "UPDATE TURN SET turn=(?)";
        try (final var connection = new ConnectMysql().create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nextTurn.turn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
