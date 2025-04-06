package janggi.infrastructure;

public class TestDatabaseProvider extends AbstractDatabaseConnectionProvider implements DatabaseConnectionProvider {

    private static final String CONFIG_PATH = "config.properties";

    private static TestDatabaseProvider instance;

    private TestDatabaseProvider() {
        super(CONFIG_PATH);
    }

    public static TestDatabaseProvider getInstance() {
        if (instance == null) {
            instance = new TestDatabaseProvider();
        }
        return instance;
    }
}
