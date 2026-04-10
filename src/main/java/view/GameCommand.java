package view;

import domain.game.Game;
import domain.game.TurnResult;

public interface GameCommand {
    TurnResult execute(Game game);
}
