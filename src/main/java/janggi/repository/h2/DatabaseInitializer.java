package janggi.repository.h2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    private static final String DIR = "game.sql";

    private DatabaseInitializer() {
    }

    public static void init() {
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            InputStream resourceAsStream = readFile();

            String sql = new BufferedReader(new InputStreamReader(resourceAsStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

            stmt.executeUpdate(sql);

        } catch (Exception e) {
            throw new IllegalStateException("DB 초기화 실패", e);
        }
    }

    private static InputStream readFile() {
        InputStream resourceAsStream = DatabaseInitializer.class
                .getClassLoader()
                .getResourceAsStream(DIR);

        if (resourceAsStream == null) {
            throw new IllegalStateException("파일 찾기 실패~");
        }
        return resourceAsStream;
    }
}
