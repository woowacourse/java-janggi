package janggi.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class TestDataInitializer {

    private boolean executed = false;

    private final DBConnection dbConnection;

    public TestDataInitializer(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void init(final String absoluteFilePath) {
        if (executed) {
            throw new IllegalStateException("이미 테스트 데이터가 초기화된 상태입니다.");
        }
        executed = true;
        try (
            final Scanner scanner = new Scanner(new FileInputStream(absoluteFilePath))
                .useDelimiter(";")
        ) {
            String statement;
            while (scanner.hasNext()) {
                statement = scanner.next();
                dbConnection.executeUpdate(statement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
