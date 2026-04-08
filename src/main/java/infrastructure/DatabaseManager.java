package infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DEFAULT_URL = "jdbc:h2:./janggi_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final String url;

    public DatabaseManager() {
        this(DEFAULT_URL);
    }

    public DatabaseManager(String url) {
        this.url = url;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    public void initSchema() {
        try (InputStream is = getClass().getResourceAsStream("/schema.sql")) {
            if (is == null) {
                throw new IllegalStateException("[ERROR] schema.sql 파일을 찾을 수 없습니다.");
            }
            String sql = new String(is.readAllBytes());
            try (Connection conn = getConnection();
                 Statement stmt = conn.createStatement()) {
                executeStatements(sql, stmt);
            }
        } catch (IOException | SQLException e) {
            throw new IllegalStateException("[ERROR] 스키마 초기화 실패: " + e.getMessage(), e);
        }
    }

    private static void executeStatements(String sql, Statement stmt) throws SQLException {
        for (String statement : sql.split(";")) {
            execute(statement, stmt);
        }
    }

    private static void execute(String statement, Statement stmt) throws SQLException {
        String trimmed = statement.trim();
        if (!trimmed.isEmpty()) {
            stmt.execute(trimmed);
        }
    }
}
