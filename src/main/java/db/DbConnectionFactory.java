package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionFactory {
    private DbConnectionFactory() {
    }

    public static Connection createConnection() throws SQLException {
        String url = ConfigLoader.getProperty("db.jdbc.url");
        String user = ConfigLoader.getProperty("db.jdbc.user");
        String password = ConfigLoader.getProperty("db.jdbc.password");
        return DriverManager.getConnection(url, user, password);
    }
}




