package config;

import init.DatabaseInitializer;

public class DatabaseConfig {

    private static final String URL = "jdbc:h2:file:./data/testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private final ConnectionManager connectionManager;

    public DatabaseConfig() {
        this.connectionManager = new H2ConnectionManager(URL, USER, PASSWORD);
    }

    public ConnectionManager connectionManager() {
        return connectionManager;
    }

    public DatabaseInitializer databaseInitializer(){
        return new DatabaseInitializer(connectionManager());
    }
}