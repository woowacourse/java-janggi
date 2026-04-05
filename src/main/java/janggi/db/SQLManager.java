package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {
    private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
    private static final boolean OPT_AUTO_COMMIT = false;
    private static final int OPT_VALID_TIMEOUT = 500;

    private Connection connection = null;
    private final String url;

    public SQLManager(String url) {
        this.url = url;
    }

    public Connection createConnection() {
        try {
            Class.forName(SQLITE_JDBC_DRIVER);
            this.connection = DriverManager.getConnection(this.url);
            this.connection.setAutoCommit(OPT_AUTO_COMMIT);

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
        return this.connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결을 닫는 중 오류가 발생했습니다.", e);
        } finally {
            connection = null;
        }
    }

    public Connection ensureConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(OPT_VALID_TIMEOUT)) {
                createConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
