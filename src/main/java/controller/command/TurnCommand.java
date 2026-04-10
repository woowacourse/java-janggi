package controller.command;

import domain.game.JanggiGame;
import service.JanggiGameService;

public sealed interface TurnCommand permits MoveCommand, PassCommand {
    void apply(JanggiGameService gameService, JanggiGame game);
}
