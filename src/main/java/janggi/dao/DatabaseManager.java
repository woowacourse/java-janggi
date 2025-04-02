package janggi.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String SERVER = "localhost:13307"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection =  DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 데이터베이스 연결에 문제가 발생했습니다.");
        }
        return connection;
    }

    public static void createInitialTable() {
        StringBuilder createTableQueryBuilder = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/janggi.sql"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                createTableQueryBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] createTableQueries = String.valueOf(createTableQueryBuilder).split(";");
        for (String createTableQuery : createTableQueries) {
            createTableQuery = createTableQuery.trim();
            if (!createTableQuery.isEmpty()) {
                executePreparedStatement(createTableQuery, PreparedStatement::executeUpdate);
            }
        }
    }

    @FunctionalInterface
    public interface PreparedStatementExecutor<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    public static <T> T executePreparedStatement(String query, PreparedStatementExecutor<T> executor) {
        try (final Connection connection = DatabaseManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 데이터베이스 작업이 성공적으로 진행되지 않았습니다.");
        }
    }
}
