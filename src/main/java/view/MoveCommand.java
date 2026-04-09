package view;

import domain.board.Position;
import domain.game.Game;

public record MoveCommand(Position from, Position to) implements GameCommand {
    @Override
    public void execute(Game game) {
        game.playMove(from, to);
    }
}
