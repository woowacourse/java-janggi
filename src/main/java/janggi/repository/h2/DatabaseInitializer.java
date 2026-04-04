package janggi.repository.h2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    private static final String DIR = "game.sql";

    private DatabaseInitializer() {
    }

    public static void init() {
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(DatabaseInitializer.class
                                    .getClassLoader()
                                    .getResourceAsStream(DIR))
                    )
            ).lines().collect(Collectors.joining("\n"));

            stmt.execute(sql);

        } catch (NullPointerException e) {
            throw new IllegalStateException("파일 찾기 실패~");
        } catch (Exception e) {
            throw new IllegalStateException("DB 초기화 실패", e);
        }
    }
}
