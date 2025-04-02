package janggi.data.fixture;

import janggi.db.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBFixture {

    public static void resetTable(DBConnector dbConnector, String tableName) {
        String query = "DELETE FROM " + tableName;
        try (
                Connection connection = dbConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
