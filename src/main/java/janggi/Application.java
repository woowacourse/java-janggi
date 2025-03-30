package janggi;

import config.Config;
import janggi.controller.JanggiController;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        Config config = new Config();
        JanggiController janggiController = config.janggiController();
        janggiController.run();
    }
}
