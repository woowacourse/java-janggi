package domain.state;

import domain.game.JanggiGame;
import domain.setup.Command;

public interface GameState {
    GamePhase phase();

    GameState handle(JanggiGame game, Command command);

}
