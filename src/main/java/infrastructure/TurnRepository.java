package infrastructure;

import domain.game.Turn;
import domain.piece.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnRepository {

    private final DbConnection dbConnection;

    public TurnRepository(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Turn findTurn() {
        String query = "SELECT current_turn FROM turn";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Turn(Country.valueOf(resultSet.getString("current_turn")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(Turn current) {
        String query = "UPDATE turn SET current_turn = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, current.getCountry().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Turn current) {
        String query = "INSERT INTO turn (current_turn) VALUES(?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, current.getCountry().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        String query = "DELETE FROM turn";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
