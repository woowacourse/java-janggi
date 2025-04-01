package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.h2.tools.RunScript;

public class H2ConnectionFactory implements ConnectionFactory {
    private static final String FILE_PATH_IN_RESOURCES = "/db-test.properties";

    @Override
    public Connection getConnection() {
        Properties properties = new Properties();

        try (InputStream input = H2ConnectionFactory.class.getResourceAsStream(FILE_PATH_IN_RESOURCES)) {
            properties.load(input);

            String url = properties.getProperty("test.db.url");
            String username = properties.getProperty("test.db.username");
            String password = properties.getProperty("test.db.password");

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 커넥션 획득에 실패했습니다.");
        } catch (IOException e) {
            throw new RuntimeException("설정 파일이 존재하지 않습니다.");
        }
    }

    public void initializeTable() {
        try (Connection connection = getConnection()) {
            RunScript.execute(connection, new FileReader("src/test/resources/schema.sql"));
        } catch (SQLException e) {
            throw new RuntimeException("테이블 초기화에 실패했습니다.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("초기화 SQL 파일이 존재하지 않습니다.");
        }
    }
}
