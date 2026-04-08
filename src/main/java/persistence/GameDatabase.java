package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class GameDatabase {

    private static final String FILE_URL = "jdbc:h2:file:./data/janggi;AUTO_SERVER=TRUE";

    private GameDatabase() {
    }

    public static Connection openFileConnection() throws SQLException {
        return DriverManager.getConnection(FILE_URL);
    }
}
