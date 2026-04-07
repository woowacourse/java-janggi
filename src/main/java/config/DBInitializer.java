package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInitializer {
    public static void init() {
        try (Connection connection = DBConnection.getConnection();
             InputStream inputStream = DBInitializer.class.getClassLoader().getResourceAsStream("init.sql")) {
            if (inputStream == null) {
                throw new RuntimeException("init.sql 파일을 찾을 수 없습니다.");
            }

            String sql = new String(inputStream.readAllBytes());
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.execute();
            }
        } catch (SQLException | IOException e) {
            System.out.println("테이블 생성 중 에러 발생: " + e.getMessage());
        }
    }
}
