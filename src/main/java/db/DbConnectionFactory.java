package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionFactory {
    private DbConnectionFactory() {
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.jdbcUrl(),
                DbConfig.user(),
                DbConfig.password()
        );
    }
}

