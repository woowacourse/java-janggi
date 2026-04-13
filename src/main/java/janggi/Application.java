package janggi;

import janggi.controller.JanggiController;
import janggi.db.H2DBConnector;

public class Application {

    public static void main(String[] args) {
        final JanggiController janggiController = new JanggiController(new H2DBConnector());
        janggiController.run();
    }
}
