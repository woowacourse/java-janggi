package janggiGame.state.Finished;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.state.GameScore;
import janggiGame.state.State;
import java.util.Map;

public abstract class Finished implements State {

    public static final String ERROR_MESSAGE = "[ERROR] 이미 게임이 종료되었습니다.";

    public boolean isFinished() {
        return true;
    }

    @Override
    public State arrangePieces(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    @Override
    public State takeTurn(Dot origin, Dot destination) {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    @Override
    public State skipTurn() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    @Override
    public GameScore getGameScore() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    @Override
    public Map<Dot, Piece> getPieces() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    @Override
    public Dynasty getCurrentDynasty() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }
}
