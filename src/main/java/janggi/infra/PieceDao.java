package janggi.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PieceDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "k-chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB 연결 오류", e);
        }
    }
}
