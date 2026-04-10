package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DBConnection implements DBConnection{
    private final String url;
    private static final String USER;
    private static final String PASSWORD;

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream inputStream = H2DBConnection.class.getClassLoader().getResourceAsStream("properties.yml")) {
            if (inputStream == null) {
                throw new IllegalStateException("properties.yml 파일을 찾을 수 없습니다.");
            }
            DBConfig config = mapper.readValue(inputStream, DBConfig.class);
            USER = config.user();
            PASSWORD = config.password();
        } catch (IOException e) {
            throw new IllegalStateException("DB 설정 파일을 읽는데 실패했습니다.", e);
        }
    }

    public H2DBConnection(String url) {
        this.url = url;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결에 실패했습니다: " + e.getMessage(), e);
        }
    }
}
