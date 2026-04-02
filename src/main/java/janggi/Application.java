package janggi;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.StandardDBConnection;
import janggi.controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        final DBConnection dbConnection = new StandardDBConnection();
        final DBTableInitializer dbTableInitializer = new DBTableInitializer(dbConnection);
        dbConnection.init();
        dbTableInitializer.init();
        final JanggiController janggiController = new JanggiController();
        janggiController.run();
    }
}
