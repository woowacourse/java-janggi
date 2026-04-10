package db.util;

import db.connector.Connector;
import java.sql.Connection;
import java.sql.SQLException;

public class TestDatabaseUtil {

    private static final Connector connector = new TestDatabaseConnector();
    private static final TestSchema schema = new TestSchema();

    public static void setUpDatabase() {
        try (Connection schemaConnection = connector.getConnection()) {
            schema.execute(schemaConnection);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
