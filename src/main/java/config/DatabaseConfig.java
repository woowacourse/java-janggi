package config;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcDataSource;

public class DatabaseConfig {

    private static final String URL = "jdbc:h2:file:./h2db/janggi;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection createConnection() {
        try {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL(URL);
            dataSource.setUser(USER);
            dataSource.setPassword(PASSWORD);

            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결에 실패했습니다: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
