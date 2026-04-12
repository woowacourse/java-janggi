package janggi;

import janggi.config.AppConfig;
import janggi.config.DbConfig;
import janggi.db.DatabaseInitializer;

public class JanggiApplication {

    public static void main(String[] args) {
        try (DbConfig dbConfig = new DbConfig()) {
            DatabaseInitializer databaseInitializer = dbConfig.databaseInitializer();
            databaseInitializer.initialize();

            AppConfig appConfig = new AppConfig(dbConfig.transactionManager());

            GameRunner gameRunner = appConfig.gameRunner();
            gameRunner.run();
        }
    }
}
