package repository.connector;

import config.DatabaseProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnector implements Connector {

    private final DatabaseProperties databaseProperties;

    public MysqlConnector(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            databaseProperties.url(),
            databaseProperties.user(),
            databaseProperties.password()
        );
    }
}
