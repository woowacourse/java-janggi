package janggi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnector implements DBConnector{
    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "janggi_test";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "100tk324dl";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalArgumentException("[ERROR] : 테스트 DB 연결 실패");
        }
    }
}
