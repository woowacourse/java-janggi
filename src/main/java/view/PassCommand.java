package view;

import domain.game.Game;

public record PassCommand() implements GameCommand {
    @Override
    public void execute(Game game) {
        game.passTurn();
    }
}
