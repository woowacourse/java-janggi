package janggi;

import janggi.config.AppConfig;
import janggi.config.DatabaseManager;
import janggi.config.DdlAuto;
import janggi.controller.JanggiController;
import janggi.util.Console;

public class Application {

    public static void main(String[] args) {
        DatabaseManager.initTable(DdlAuto.CREATE);

        AppConfig appConfig = new AppConfig();
        JanggiController controller = appConfig.controller();
        try {
            controller.run();
        } finally {
            Console.close();
        }
    }

}
