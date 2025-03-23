package janggi;

import janggi.controller.GameController;
import janggi.view.View;

public class Application {

    public static void main(String[] args) {
        GameController gameController = new GameController(new View());
        gameController.runGame();
    }
}
