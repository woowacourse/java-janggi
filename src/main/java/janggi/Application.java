package janggi;

import janggi.config.AppConfig;
import janggi.config.DatabaseManager;
import janggi.controller.JanggiController;
import janggi.util.Console;

public class Application {

    public static void main(String[] args) {
        DatabaseManager.initTable();

        AppConfig appConfig = new AppConfig();
        JanggiController controller = appConfig.controller();
        try {
            controller.run();
        } finally {
            Console.close();
        }
    }

}
