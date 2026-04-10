package controller.command;

import domain.game.JanggiGame;
import domain.position.Position;
import service.JanggiGameService;

public record MoveCommand(Position source, Position destination) implements TurnCommand {
    @Override
    public void apply(JanggiGameService gameService, JanggiGame game) {
        gameService.move(game, source, destination);
    }
}
