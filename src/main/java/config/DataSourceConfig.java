package config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {
    public static DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/janggi;AUTO_SERVER=TRUE");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
