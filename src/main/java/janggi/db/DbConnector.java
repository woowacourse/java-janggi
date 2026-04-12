package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class DbConnector {
    private static final String URL = "jdbc:h2:tcp://localhost/~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final DataSource DATA_SOURCE;

    static {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(URL);
        jdbcDataSource.setUser(USER);
        jdbcDataSource.setPassword(PASSWORD);
        DATA_SOURCE = jdbcDataSource;
    }

    public static Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다. 서버가 켜져 있는지 확인해 주세요.", e);
        }
    }
}
