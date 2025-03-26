package janggiGame;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.state.GameResult;
import janggiGame.state.GameScore;
import janggiGame.state.Started.Started;
import janggiGame.state.State;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class JanggiGame {
    Deque<State> history = new ArrayDeque<>();
    private State currentState = new Started();

    public void arrangePieces(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        currentState = currentState.arrangePieces(hanStrategy, choStrategy);
    }

    public void takeTurn(Dot origin, Dot destination) {
        history.push(currentState);
        currentState = currentState.takeTurn(origin, destination);
    }

    public void skipTurn() {
        history.push(currentState);
        currentState = currentState.skipTurn();
    }

    public void arrangeChoPieces(ArrangementStrategy strategy) {
        pieces.putAll(strategy.arrangeCho(Dynasty.CHO));
    }

    public Map<Dot, Piece> getPieces() {
        return currentState.getPieces();
    }

    public Dynasty getCurrentDynasty() {
        return currentState.getCurrentDynasty();
    }
}
