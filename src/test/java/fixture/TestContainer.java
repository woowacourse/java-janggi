package fixture;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;


public class TestContainer {

    @Container
    public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("root");

    static {
        mysql.start();
        initializeSchema();
    }

    private static void initializeSchema() {
        try (final Connection conn = DriverManager.getConnection(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword())) {
            final Statement statement = conn.createStatement();
            final String sql = Files.readString(Path.of("src/test/resources/testSchema.sql"));
            for (final String query : sql.split(";")) {
                if (!query.isBlank()) {
                    statement.execute(query.trim());
                }
            }
        } catch (final IOException | SQLException e) {
            throw new RuntimeException("스키마 초기화 실패", e);
        }
    }

    public static void truncateAll() {
        try (final Connection conn = getConnection()) {
            final Statement stmt = conn.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            stmt.execute("TRUNCATE TABLE piece");
            stmt.execute("TRUNCATE TABLE point");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
        } catch (final SQLException e) {
            throw new RuntimeException("테이블 초기화 실패", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword()
        );
    }
}
