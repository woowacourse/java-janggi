package controller.command;

import domain.game.JanggiGame;
import service.JanggiGameService;

public record PassCommand() implements TurnCommand {
    @Override
    public void apply(JanggiGameService gameService, JanggiGame game) {
        gameService.pass(game);
    }
}
