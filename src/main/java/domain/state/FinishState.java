package domain.state;

import domain.game.GameResult;
import domain.game.JanggiGame;
import domain.setup.Command;

public class FinishState implements GameState {
    private final GameResult gameResult;

    public FinishState(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    @Override
    public GamePhase phase() {
        return GamePhase.FINISH;
    }

    @Override
    public GameState handle(JanggiGame game, Command command) {
        return this;
    }

    public GameResult getGameResult() {
        return gameResult;
    }
}
