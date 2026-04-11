package janggi.controller;

import java.util.function.Function;

public enum GameCommand {
    NEW_GAME(ConsoleController::initializeGame),
    LOAD_GAME(ConsoleController::loadGame);

    private final Function<ConsoleController, GameSession> action;

    GameCommand(Function<ConsoleController, GameSession> action) {
        this.action = action;
    }

    public GameSession execute(ConsoleController controller) {
        return action.apply(controller);
    }
}
