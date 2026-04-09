package janggi.config;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class TestDataSourceConfig {

    private static final String URL = "jdbc:h2:mem:test";
    private static final String USER_NAME = "sa";
    private static final String PASSWORD = "";

    public static DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();

        dataSource.setURL(URL);
        dataSource.setUser(USER_NAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
