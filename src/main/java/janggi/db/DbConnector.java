package janggi.db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class DbConnector {
    private static final String URL = "jdbc:h2:tcp://localhost/~/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final DataSource DATA_SOURCE;

    static {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(URL);
        jdbcDataSource.setUser(USER);
        jdbcDataSource.setPassword(PASSWORD);
        DATA_SOURCE = jdbcDataSource;
    }

    public static Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다. 서버가 켜져 있는지 확인해 주세요.", e);
        }
    }

    public static void initDatabase() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = Files.readString(Path.of("src/main/resources/schema.sql"));
            statement.execute(sql);
            System.out.println("[INFO] 데이터베이스 테이블이 초기화되었습니다.");
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] DB 초기화 중 오류가 발생했습니다.", e);
        }
    }
}
