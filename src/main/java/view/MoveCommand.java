package view;

import domain.board.Position;
import domain.game.Game;
import domain.game.TurnResult;

public record MoveCommand(Position from, Position to) implements GameCommand {
    @Override
    public TurnResult execute(Game game) {
        return game.playMove(from, to);
    }
}
