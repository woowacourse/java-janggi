package repository;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {
        Properties properties = new Properties();

        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결에 실패했습니다. " + e);
        } catch (IOException e) {
            throw new RuntimeException("[ERROR] 파일을 불러오는데 실패하였습니다. " + e);
        }
    }
}
