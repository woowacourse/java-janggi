package infrastructure;

import domain.game.Turn;
import domain.piece.Country;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnRepository {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Turn findTurn() {
        String query = "SELECT current_turn FROM turn";

        try (Connection connection = getConnection();
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

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, current.getCountry().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Turn current) {
        String query = "INSERT INTO turn (current_turn) VALUES(?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, current.getCountry().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
