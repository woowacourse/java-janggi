package repository.mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import repository.Connector;

public final class TestConnector implements Connector {
    private static final String SERVER = "localhost:13307";
    private static final String DATABASE = "wodnd0131";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            final Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION,
                    USERNAME, PASSWORD);
            return connection;
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            throw new SQLException(e);
        }
    }

    public static void createGameForTest(Connection connection) {
        final String query = "INSERT INTO game (id,is_active) VALUES (?,?)";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setBoolean(2, true);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
