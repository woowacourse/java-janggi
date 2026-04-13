package repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import common.DatabaseException;
import java.sql.Connection;
import java.sql.SQLException;

public class H2ConnectionManager implements ConnectionManager {

    private static final String DEFAULT_URL = "jdbc:h2:./data/janggi";
    private static final String DEFAULT_USER = "sa";
    private static final String DEFAULT_PASSWORD = "";

    private final HikariDataSource dataSource;

    public H2ConnectionManager() {
        this(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public H2ConnectionManager(String url, String user, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setPoolName("janggi-h2-pool");
        this.dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseException("데이터베이스 연결에 실패했습니다.");
        }
    }
}
