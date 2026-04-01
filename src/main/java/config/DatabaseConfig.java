package config;

import init.DatabaseInitializer;
import java.sql.Connection;

public class DatabaseConfig {

    private final Connection connectionManager;

    public DatabaseConfig(Connection connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);
        databaseInitializer.init();
    }
}