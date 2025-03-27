package fixture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DatabaseConnectionFixture {

    public static final String TEST_PORT = "23306";
    private static final String IP = "localhost";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String TEST_DATABASE_NAME = "janggi_test";

    public static Connection getTestConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + joinURL(IP, TEST_PORT) + "/" + TEST_DATABASE_NAME
                            + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류:" + e.getMessage());
        }
    }

    private static String joinURL(String ip, String port) {
        return String.join(":", List.of(ip, port));
    }
}
