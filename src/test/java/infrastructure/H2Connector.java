package infrastructure;

import infrastructure.dao.DbConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connector implements DbConnector {

    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
