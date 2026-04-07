package janggi;

import janggi.config.AppConfig;
import janggi.db.DatabaseInitializer;

public class JanggiApplication {

    public static void main(String[] args) {
        try (AppConfig config = new AppConfig()) {
            DatabaseInitializer databaseInitializer = config.databaseInitializer();
            databaseInitializer.initialize();

            GameRunner gameRunner = config.gameRunner();
            gameRunner.run();
        }
    }
}
