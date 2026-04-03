package janggi.infra.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceConfig {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();
        InputStream inputStream = DataSourceConfig.class.getClassLoader()
                .getResourceAsStream("application.properties");
        try {
            properties.load(inputStream);
            URL = properties.getProperty("db.url");
            USERNAME = properties.getProperty("db.username");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSource dataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(URL);
        jdbcDataSource.setUser(USERNAME);
        jdbcDataSource.setPassword(PASSWORD);

        return jdbcDataSource;
    }
}
