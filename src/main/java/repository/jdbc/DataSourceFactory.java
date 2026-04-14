package repository.jdbc;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    public DataSource create() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/janggi");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
