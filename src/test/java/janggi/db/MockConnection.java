package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MockConnection extends DBConnection {
    private static final String JDBC_URL_BASE = "jdbc:h2:mem:";
    private static final String JANGGI_DB = "janggi";
    private static final String DEFAULT_DB = "defaultTest";
    private static final String OPTIONS = ";MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    @Override
    public Connection getJanggiConnection() {
        try {
            Class.forName("org.h2.Driver");
            String url = JDBC_URL_BASE + JANGGI_DB + OPTIONS;
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("장기 DB 연결 오류: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("H2 드라이버를 찾을 수 없습니다: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            String url = JDBC_URL_BASE + DEFAULT_DB + OPTIONS;
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("기본 DB 연결 오류: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("H2 드라이버를 찾을 수 없습니다: " + e.getMessage());
            return null;
        }
    }
}
