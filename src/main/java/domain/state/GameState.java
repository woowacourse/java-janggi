package domain.state;

import domain.setup.Command;
import domain.game.JanggiGame;
import io.OutputView;

public interface GameState {
    GameState handle(JanggiGame game, Command command);
    void display(JanggiGame game, OutputView outputView);

    default boolean isFinished() {
        return false;
    }
}
