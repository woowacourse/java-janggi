package janggi.infra.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataSourceConfig {

    private static final DataSource dataSource;

    static {
        Properties properties = new Properties();
        InputStream inputStream = DataSourceConfig.class.getClassLoader()
                .getResourceAsStream("application-test.properties");
        try {
            properties.load(inputStream);
            String URL = properties.getProperty("db.url");
            String USERNAME = properties.getProperty("db.username");
            String PASSWORD = properties.getProperty("db.password");

            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setURL(URL);
            jdbcDataSource.setUser(USERNAME);
            jdbcDataSource.setPassword(PASSWORD);
            dataSource = jdbcDataSource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSource dataSource() {
        return dataSource;
    }
}
