package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestMySQLConnection implements DatabaseConnection {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "test_janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    public TestMySQLConnection() {
        createDatabaseIfNotExists();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    private void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + SERVER + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", USERNAME,
                PASSWORD);
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE;
            statement.executeUpdate(createDatabaseQuery);
        } catch (SQLException e) {
            throw new PersistenceFailException(e.getMessage());
        }
    }
}
