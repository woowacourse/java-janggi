package domain.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JanggiDBConnect {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?serverTimezone=Asia/Seoul&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static Connection connect = null;

    private JanggiDBConnect() {
    }

    public static Connection getConnection() {
        if (connect == null) {
            try {
                connect = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
            } catch (final SQLException e) {
                throw new IllegalStateException("[ERROR] DB 연결 오류");
            }
        }
        return connect;
    }
}
