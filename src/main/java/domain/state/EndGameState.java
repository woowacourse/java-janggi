package domain.state;

import domain.game.JanggiGame;
import domain.setup.Command;
import io.OutputView;

public class EndGameState implements GameState {
    private final GameResult result;

    public EndGameState(GameResult result) {
        this.result = result;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        return this;
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printGameResult(result, game.getBoard().orElseThrow());
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public GameStateName stateName() {
        return GameStateName.END;
    }
}
