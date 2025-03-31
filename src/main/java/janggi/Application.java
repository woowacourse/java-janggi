package janggi;

import janggi.db.BoardStatus;
import janggi.game.GameController;

public class Application {
    public static void main(String[] args) {
        GameController gameController = new GameController(new BoardStatus());
        gameController.run();
    }
}
