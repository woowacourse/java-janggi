package domain.state;

import domain.command.Command;
import domain.game.JanggiGame;
import io.OutputView;

public interface GameState {
    GameState handle(JanggiGame game, Command command);
    void display(JanggiGame game, OutputView outputView);

}
