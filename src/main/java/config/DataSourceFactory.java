package config;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public final class DataSourceFactory {

    private DataSourceFactory() {
    }

    public static DataSource create() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl("jdbc:h2:./data/janggi");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");

        return jdbcDataSource;
    }
}
