package janggi.infra;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataConnectionManager {

    private final DataSource dataSource;

    public DataConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
