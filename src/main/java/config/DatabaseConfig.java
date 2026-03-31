package config;

import init.DatabaseInitializer;

public class DatabaseConfig {

    private final H2ConnectionManager connectionManager;

    public DatabaseConfig(H2ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() {
        DatabaseInitializer.init(connectionManager);
    }
}