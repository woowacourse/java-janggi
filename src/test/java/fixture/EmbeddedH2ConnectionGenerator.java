package fixture;

import dao.init.ConnectionGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EmbeddedH2ConnectionGenerator implements ConnectionGenerator {

    static {
        setUp();
    }

    @Override
    public Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:janggi;DB_CLOSE_DELAY=-1", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setUp() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:janggi;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "sa", "")) {
            try {
                String sqls = new String(Files.readAllBytes(Paths.get("src/test/resources/create_table.sql")));
                Statement statement = conn.createStatement();
                for (String sql : sqls.split(";")) {
                    String stripped = sql.strip();
                    if (!stripped.isEmpty()) {
                        statement.execute(stripped);
                    }
                }
                statement.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
