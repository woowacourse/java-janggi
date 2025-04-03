package db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DatabaseConnector {

    private final DataSource dataSource;

    public DatabaseConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }
}
