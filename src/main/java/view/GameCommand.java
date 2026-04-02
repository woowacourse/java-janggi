package view;

import domain.game.Game;

public interface GameCommand {
    void execute(Game game);
}
