package janggiGame.state.Started;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.state.GameResult;
import janggiGame.state.GameScore;
import janggiGame.state.Running.ChoTurn;
import janggiGame.state.State;
import java.util.HashMap;
import java.util.Map;

public class Started implements State {

    public static final String ERROR_MESSAGE = "[ERROR] 아직 장기판이 초기화되지 않았습니다.";

    @Override
    public State arrangePieces(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        Map<Dot, Piece> pieces = new HashMap<>();
        pieces.putAll(arrangeHanPieces(hanStrategy));
        pieces.putAll(arrangeChoPieces(choStrategy));
        return new ChoTurn(pieces, false);
    }

    private Map<Dot, Piece> arrangeHanPieces(ArrangementStrategy strategy) {
        return strategy.arrangeHan(Dynasty.HAN);
    }

    private Map<Dot, Piece> arrangeChoPieces(ArrangementStrategy strategy) {
        return strategy.arrangeCho(Dynasty.CHO);
    }

    @Override
    public boolean isFinished() {
        return false;
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
    public GameResult getGameResult() {
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
