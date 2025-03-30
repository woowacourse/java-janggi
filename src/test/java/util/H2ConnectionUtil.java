package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public class H2ConnectionUtil {
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 커넥션 획득에 실패했습니다.");
        }
    }

    public static void initializeTable(Connection connection) {
        try {
            RunScript.execute(connection, new FileReader("src/test/resources/schema.sql"));
        } catch (SQLException e) {
            throw new RuntimeException("테이블 초기화에 실패했습니다.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("초기화 SQL 파일이 존재하지 않습니다.");
        }
    }
}
