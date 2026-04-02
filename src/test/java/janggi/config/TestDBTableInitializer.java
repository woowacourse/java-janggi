package janggi.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public final class TestDBTableInitializer {

    private static final String ABSOLUTE_SQL_FILE_PATH = "./src/main/resources/schema.sql";
    private static boolean executed;

    private TestDBTableInitializer() {

    }

    public static void init() {
        if (executed) {
            throw new IllegalStateException("이미 DB 테이블이 초기화된 상태입니다.");
        }
        executed = true;
        try (
            final Scanner scanner = new Scanner(new FileInputStream(ABSOLUTE_SQL_FILE_PATH))
                .useDelimiter(";")
        ) {
            String statement;
            while (scanner.hasNext()) {
                statement = scanner.next();
                TestDBConnection.executeUpdate(statement);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

