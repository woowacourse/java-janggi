package janggi;

import janggi.board.controller.Controller;
import janggi.board.controller.OfflineController;
import janggi.view.View;

public final class Application {

    public static void main(String[] args) {
        Controller controller = new OfflineController(new View());
        controller.gameStart();
    }
}
