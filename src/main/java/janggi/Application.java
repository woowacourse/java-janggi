package janggi;

import janggi.config.DBConnection;
import janggi.controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        DBConnection.init();
        final JanggiController janggiController = new JanggiController();
        janggiController.run();
    }
}
