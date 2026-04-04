package janggi;

import janggi.controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        final JanggiController janggiController = new JanggiController();
        janggiController.run();
    }
}
