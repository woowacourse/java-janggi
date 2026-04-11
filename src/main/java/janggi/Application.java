package janggi;

import janggi.config.AppConfig;
import janggi.controller.JanggiController;
import janggi.repository.DBConnectionProvider;

public class Application {
    public static void main(String[] args) {
        DBConnectionProvider.initDatabase();
        AppConfig config = new AppConfig();
        JanggiController controller = config.janggiController();
        controller.run();
    }
}
