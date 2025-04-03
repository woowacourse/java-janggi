package fixture;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestContainer {

    @Container
    public static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

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
            statement.execute("""
                        CREATE TABLE game
                        (
                            id            BIGINT AUTO_INCREMENT        NOT NULL,
                            status        ENUM ('RUNNING', 'FINISHED') NOT NULL,
                            turn          INT                          NOT NULL,
                            cho_score     INT                          NOT NULL,
                            han_score     INT                          NOT NULL,
                            started_at      TIMESTAMP                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            last_saved_at TIMESTAMP                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (id)
                        );
                    """);

            statement.execute("""
                         CREATE TABLE piece
                         (
                             id         BIGINT AUTO_INCREMENT                                                          NOT NULL,
                             game_id    BIGINT                                                                         NOT NULL,
                             row_pos    INT                                                                            NOT NULL,
                             column_pos INT                                                                            NOT NULL,
                             type       ENUM ('GENERAL', 'GUARD', 'SOLDIER', 'HORSE', 'ELEPHANT', 'CHARIOT', 'CANNON') NOT NULL,
                             team       ENUM ('CHO', 'HAN'),
                             PRIMARY KEY (id)
                         );
                    """);
        } catch (final SQLException e) {
            throw new RuntimeException("스키마 초기화 실패", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                mysql.getJdbcUrl(),
                mysql.getUsername(),
                mysql.getPassword()
        );
    }

    public static void truncateAll() {
        try (final Connection conn = getConnection()) {
            final Statement stmt = conn.createStatement();
            stmt.execute("TRUNCATE TABLE game");
            stmt.execute("TRUNCATE TABLE piece");
        } catch (final SQLException e) {
            throw new RuntimeException("테이블 초기화 실패", e);
        }
    }
}
