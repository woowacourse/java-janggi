package janggi.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class MySQLDBConnectorTest {

    private static final String TEST_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"; // H2 인메모리 DB
    private static final String TEST_USERNAME = "sa";
    private static final String TEST_PASSWORD = "";

    private Connection connection;
    private MySQLDBConnector mySQLDBConnector;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(TEST_DB_URL, TEST_USERNAME, TEST_PASSWORD);
        mySQLDBConnector = new MySQLDBConnector() {
            @Override
            public Connection getConnection() {
                return connection; // 테스트용 DB 연결 반환
            }
        };
        mySQLDBConnector.createTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
        }
        connection.close();
    }

    @Test
    void 데이터베이스_연결_테스트() {
        assertDoesNotThrow(() -> {
            Connection conn = mySQLDBConnector.getConnection();
            assertNotNull(conn);
        });
    }

    @Test
    void 테이블_생성_테스트() throws SQLException {
        assertTrue(테이블_존재_확인("team"));
        assertTrue(테이블_존재_확인("pieces"));
        assertTrue(테이블_존재_확인("turn"));
    }

    private boolean 테이블_존재_확인(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + tableName.toUpperCase() + "'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
