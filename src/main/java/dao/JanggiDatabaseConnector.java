package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JanggiDatabaseConnector implements DatabaseConnector {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = JanggiDatabaseConnector.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties 파일이 존재하지 않습니다.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("application.properties 파일 로딩 실패", e);
        }
    }

    private static final String SERVER = properties.getProperty("db.server"); // MySQL 서버 주소
    private static final String DATABASE = properties.getProperty("db.database"); // MySQL DATABASE 이름
    private static final String OPTION = properties.getProperty("db.option");
    private static final String USERNAME = properties.getProperty("db.username"); //  MySQL 서버 아이디
    private static final String PASSWORD = properties.getProperty("db.password"); // MySQL 서버 비밀번호

    @Override
    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
