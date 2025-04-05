package database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2ConnectManager implements ConnectionManager {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private Connection connection;

    public H2ConnectManager() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            runInitScript(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void runInitScript(Connection conn) {
        try (var reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("../resources/init.sql")))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String[] statements = sb.toString().split(";");
            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    if (!sql.trim().isEmpty()) {
                        stmt.execute(sql);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("init.sql 실행 실패", e);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("커넥션 생성 실패", e);
        }
    }
}
