package janggi.controller;

import java.util.function.Consumer;

public enum GameCommand {
    NEW_GAME(ConsoleController::initializeGame),
    LOAD_GAME(ConsoleController::loadGame);

    private final Consumer<ConsoleController> action;

    GameCommand(Consumer<ConsoleController> action) {
        this.action = action;
    }

    public void execute(ConsoleController controller) {
        action.accept(controller);
    }
}
