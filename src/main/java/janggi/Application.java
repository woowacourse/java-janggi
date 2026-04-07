package janggi;

import static janggi.db.InitDatabaseTable.initDatabaseTable;

import janggi.controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        initDatabaseTable();
        final JanggiController janggiController = new JanggiController();
        janggiController.run();
    }
}
