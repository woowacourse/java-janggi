package janggi.db;

import janggi.repository.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConnectionManager {

    private static final String DATABASE_CONNECTION_FAILED = "[ERROR] 데이터베이스 연결 중 문제가 발생했습니다.";

    private final DataSource dataSource;

    public ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException(DATABASE_CONNECTION_FAILED, e);
        }
    }
}
