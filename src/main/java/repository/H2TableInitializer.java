package repository;

import common.DatabaseException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class H2TableInitializer {

    private static final String SQL_FILE_PATH = "./src/main/resources/schema.sql";

    private final ConnectionManager connectionManager;

    public H2TableInitializer(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initialize() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();
             Scanner scanner = new Scanner(new FileInputStream(SQL_FILE_PATH)).useDelimiter(";")) {
            while (scanner.hasNext()) {
                String query = scanner.next().trim();
                if (query.isBlank()) {
                    continue;
                }
                statement.execute(query);
            }
        } catch (IOException | SQLException e) {
            throw new DatabaseException("데이터베이스 초기화에 실패했습니다.");
        }
    }
}
