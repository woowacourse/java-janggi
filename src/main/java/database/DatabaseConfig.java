package database;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DatabaseConfig {
    private static final String DEFAULT_DIRECTORY = "storage";
    private static final String DEFAULT_DATABASE_NAME = "janggi";
    private static final String DEFAULT_USER = "sa";
    private static final String DEFAULT_PASSWORD = "";
    private static final String FILE_JDBC_PREFIX = "jdbc:h2:file:";
    private static final String MEMORY_JDBC_PREFIX = "jdbc:h2:mem:";
    private static final String COMMON_JDBC_OPTIONS = ";MODE=MySQL;DB_CLOSE_DELAY=-1";
    private static final String FILE_JDBC_OPTIONS = COMMON_JDBC_OPTIONS + ";AUTO_SERVER=TRUE";

    private final String jdbcUrl;
    private final String user;
    private final String password;

    public DatabaseConfig() {
        this(createDefaultJdbcUrl(), DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public DatabaseConfig(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    public static DatabaseConfig inMemory(String databaseName) {
        return new DatabaseConfig(
                MEMORY_JDBC_PREFIX + databaseName + COMMON_JDBC_OPTIONS,
                DEFAULT_USER,
                DEFAULT_PASSWORD
        );
    }

    private static String createDefaultJdbcUrl() {
        Path databasePath = Paths.get(DEFAULT_DIRECTORY, DEFAULT_DATABASE_NAME).toAbsolutePath();
        return FILE_JDBC_PREFIX + databasePath + FILE_JDBC_OPTIONS;
    }

    public String jdbcUrl() {
        return jdbcUrl;
    }

    public String user() {
        return user;
    }

    public String password() {
        return password;
    }
}
