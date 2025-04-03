package dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDBConnector implements DBConnector {

    private static final String IP = "localhost";
    private static final String PORT = "13306";
    private static final String DATABASE_NAME = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE_NAME + OPTION, USERNAME, PASSWORD
            );
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.");
        }
    }

}
