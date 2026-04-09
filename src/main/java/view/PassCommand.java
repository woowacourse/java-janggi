package view;

import domain.game.Game;
import domain.game.TurnResult;

public record PassCommand() implements GameCommand {
    @Override
    public TurnResult execute(Game game) {
        return game.passTurn();
    }
}
