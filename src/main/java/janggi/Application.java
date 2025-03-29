package janggi;

import janggi.controller.Controller;
import janggi.data.DatabaseConnection;

public final class Application {

    public static void main(String[] args) {
        Controller controller = createController();
        controller.gameStart();
    }

    private static Controller createController() {
        GameMode gameMode = GameMode.from(DatabaseConnection.isConnected());
        return gameMode.createController();
    }
}
