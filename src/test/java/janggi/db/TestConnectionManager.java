package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class TestConnectionManager implements ConnectionManager {

    private static final String UNABLE_TO_ACCESS_DATABASE = "[ERROR] H2 데이터베이스에 연결할 수 없습니다.";
    private static final String URL = "jdbc:h2:mem:test-db;DB_CLOSE_DELAY=-1";

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException e) {
            throw new IllegalStateException(UNABLE_TO_ACCESS_DATABASE);
        }
    }

    public void clear() {
        try (
                Connection connection = createConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute("DROP ALL OBJECTS");
        } catch (SQLException e) {
            throw new IllegalStateException(UNABLE_TO_ACCESS_DATABASE);
        }
    }
}
