package repository;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {

    private static final String CONFIG_FILE = "db.properties";

    public static DataSource getConnection() {
        Properties properties = new Properties();

        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("[ERROR] " + CONFIG_FILE + " 파일을 찾을 수 없습니다.");
            }
            properties.load(input);

            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(properties.getProperty("db.url"));
            mysqlDataSource.setUser(properties.getProperty("db.user"));
            mysqlDataSource.setPassword(properties.getProperty("db.password"));

            return mysqlDataSource;
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] DB 연결 실패: " + e.getMessage());
        }
    }
}
