package janggiGame.state.Finished;

import janggiGame.state.GameResult;

public class Draw extends Finished{
    @Override
    public GameResult getGameResult() {
        return GameResult.DRAW;
    }
}
