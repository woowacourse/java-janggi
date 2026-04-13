package controller.command;

import repository.StoredGame;
import service.JanggiGameService;

public record PassCommand() implements TurnCommand {
    @Override
    public void apply(JanggiGameService gameService, StoredGame stored) {
        gameService.pass(stored);
    }
}
