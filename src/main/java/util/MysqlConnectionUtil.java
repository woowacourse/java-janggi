package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnectionUtil {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 커넥션 획득에 실패했습니다.");
        }
    }
}
