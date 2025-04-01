package fixture;

import dao.init.ConnectionGenerator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TestMySQLConnectionGenerator implements ConnectionGenerator {

    public static final String TEST_PORT = "23306";
    private static final String IP = "localhost";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String TEST_DATABASE_NAME = "janggi";

    private String joinURL(String ip, String port) {
        return String.join(":", List.of(ip, port));
    }

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + joinURL(IP, TEST_PORT) + "/" + TEST_DATABASE_NAME
                            + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다. : " + e.getMessage());
        }
    }
}
