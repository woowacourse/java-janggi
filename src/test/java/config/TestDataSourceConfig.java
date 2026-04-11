package config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestDataSourceConfig {

    public static DataSource testDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
