package janggi.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class DBTableInitializer {

    private static final String ABSOLUTE_SQL_FILE_PATH = "./src/main/resources/schema.sql";

    private final DBConnection dbConnection;

    public DBTableInitializer(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void init() {
        try (
            final Scanner scanner = new Scanner(new FileInputStream(ABSOLUTE_SQL_FILE_PATH))
                .useDelimiter(";")
        ) {
            while (scanner.hasNext()) {
                final String statement = scanner.next();
                dbConnection.executeUpdate(statement);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
