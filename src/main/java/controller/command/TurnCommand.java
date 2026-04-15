package controller.command;

import repository.StoredGame;
import service.JanggiGameService;

public sealed interface TurnCommand permits MoveCommand, PassCommand {
    void apply(JanggiGameService gameService, StoredGame stored);
}
