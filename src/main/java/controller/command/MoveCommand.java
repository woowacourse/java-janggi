package controller.command;

import domain.position.Position;
import repository.StoredGame;
import service.JanggiGameService;

public record MoveCommand(Position source, Position destination) implements TurnCommand {
    @Override
    public void apply(JanggiGameService gameService, StoredGame stored) {
        gameService.move(stored, source, destination);
    }
}
