package janggi.config;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class DataSourceConfig {

    private static final String URL = "jdbc:h2:~/janggi;AUTO_SERVER=TRUE";
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
