package janggi.config;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class TestDataSourceConfig {

    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";

    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            JdbcDataSource h2DataSource = new JdbcDataSource();
            h2DataSource.setURL(URL);
            h2DataSource.setUser(USER_NAME);
            h2DataSource.setPassword(PASSWORD);
            dataSource = h2DataSource;
        }
        return dataSource;
    }
}
