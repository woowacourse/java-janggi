package janggi;

import janggi.config.AppConfig;
import janggi.persistence.DatabaseInitializer;

public class Application {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        DatabaseInitializer databaseInitializer = appConfig.databaseInitializer();
        databaseInitializer.initialize();
        appConfig.janggiRunner().execute();
    }
}
