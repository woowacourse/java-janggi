package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnectionFactory implements ConnectionFactory {
    private static final String FILE_PATH_IN_RESOURCES = "/db.properties";

    @Override
    public Connection getConnection() {
        Properties properties = new Properties();

        try (InputStream input = MysqlConnectionFactory.class.getResourceAsStream(FILE_PATH_IN_RESOURCES)) {
            properties.load(input);

            String server = properties.getProperty("mysql.server");
            String database = properties.getProperty("mysql.database");
            String option = properties.getProperty("mysql.option");
            String username = properties.getProperty("mysql.username");
            String password = properties.getProperty("mysql.password");

            return DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + option, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("데이터베이스 커넥션 획득에 실패했습니다.");
        } catch (IOException e) {
            throw new RuntimeException("설정 파일이 존재하지 않습니다.");
        }
    }
}
