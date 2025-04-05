package util;

import dao.JanggiConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class TestDatabaseInitializer {

    public static void init() {
        try (Connection connection = new JanggiConnection().getConnection();
             Statement statement = connection.createStatement()) {

            String sql = Files.readString(Paths.get("src/test/resources/init-insert-only.sql"));
            for (String query : sql.split(";")) {
                String trimmed = query.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("테스트 데이터 초기화 실패", e);
        }
    }
}
