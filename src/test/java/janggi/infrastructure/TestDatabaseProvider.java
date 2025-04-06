package janggi.infrastructure;

public class TestDatabaseProvider extends AbstractDatabaseConnectionProvider implements DatabaseConnectionProvider {

    private static final String CONFIG_PATH = "config.properties";

    private static ProductionDatabaseProvider instance;

    public TestDatabaseProvider() {
        super(CONFIG_PATH);
    }

    public static ProductionDatabaseProvider getInstance() {
        if (instance == null) {
            instance = new ProductionDatabaseProvider();
        }
        return instance;
    }
}
