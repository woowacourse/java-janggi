package janggi;

import janggi.controller.JanggiController;
import janggi.infrastructure.DBInitializer;

public class Application {
    public static void main(String[] args) {
        DBInitializer.initialize();
        JanggiController janggiController = new JanggiController();
        janggiController.run();
    }
}
