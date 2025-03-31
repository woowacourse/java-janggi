package janggi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JanggiConnection {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void commit(Connection connection) {
        try {
            connection.commit();
            closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("커밋을 실행 할 수 없습니다:" + e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void rollBack(Connection connection) {
        try {
            connection.rollback();
            closeConnection(connection);
        } catch (SQLException e) {
            System.out.println("롤백을 실행 할 수 없습니다:" + e.getMessage());
            throw new RuntimeException();
        }
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("커넥션을 닫을 수 없습니다:" + e.getMessage());
            throw new RuntimeException();
        }
    }
}
