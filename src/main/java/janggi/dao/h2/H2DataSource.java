package janggi.dao.h2;

import janggi.dao.ConnectionHolder;
import janggi.dao.JdbcDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2DataSource implements JdbcDataSource {
    private static final String URL = "jdbc:h2:~/janggi;INIT=RUNSCRIPT FROM 'classpath:janggi.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final JdbcConnectionPool connectionPool;

    public H2DataSource() {
        this.connectionPool = JdbcConnectionPool.create(URL, USER, PASSWORD);
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = ConnectionHolder.get();

        if (conn != null) {
            return conn;
        }

        return connectionPool.getConnection();
    }

}
