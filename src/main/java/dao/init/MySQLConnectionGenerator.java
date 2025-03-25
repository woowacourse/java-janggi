package dao.init;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLConnectionGenerator implements ConnectionGenerator {

    private static final String IP = "localhost";
    private static final String DATABASE_NAME = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String PORT = "13306";

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + joinURL(IP, PORT) + "/" + DATABASE_NAME
                            + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다. : " + e.getMessage());
        }
    }

    private String joinURL(String ip, String port) {
        return String.join(":", List.of(ip, port));
    }
}
