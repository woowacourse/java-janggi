package janggi;

import config.Config;
import janggi.controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        Config config = new Config();
        JanggiController janggiController = config.janggiController();
        janggiController.run();
    }
}
