package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String url;
    private final String user;
    private final String password;

    public DBConnection(String url) {
        this.url = url;
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties.yml")) {
            Config config = mapper.readValue(inputStream, Config.class);
            this.user = config.user();
            this.password = config.password();
        } catch (IOException e) {
            throw new IllegalStateException("DB 설정 파일을 읽는데 실패했습니다.", e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결에 실패했습니다: " + e.getMessage(), e);
        }
    }
}
