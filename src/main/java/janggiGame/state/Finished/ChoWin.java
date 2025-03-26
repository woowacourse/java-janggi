package janggiGame.state.Finished;

import janggiGame.state.GameResult;

public class ChoWin extends Finished {
    @Override
    public GameResult getGameResult() {
        return GameResult.CHO_WIN;
    }
}
