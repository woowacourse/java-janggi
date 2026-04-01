package janggi;

import janggi.config.AppConfig;
import janggi.controller.JanggiController;

public class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        JanggiController controller = config.janggiController();
        controller.run();
    }
}
