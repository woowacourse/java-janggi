package persistence.datasource;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public final class H2DataSourceFactory {

    public DataSource create() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/janggi");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
