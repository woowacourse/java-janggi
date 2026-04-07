package janggi;

import janggi.config.AppConfig;
import janggi.db.ConnectionManager;
import janggi.db.DatabaseInitializer;

public class JanggiApplication {

    public static void main(String[] args) {
        AppConfig config = new AppConfig();

        ConnectionManager connectionManager = config.connectionManager();
        new DatabaseInitializer(connectionManager).initialize();

        GameRunner gameRunner = config.gameRunner();
        gameRunner.run();
    }
}
