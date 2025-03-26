package janggiGame;

import janggiGame.arrangement.ArrangementStrategy;
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

    public boolean isFinished() {
        return currentState.isFinished();
    }

    public GameResult getGameResult() {
        return currentState.getGameResult();
    }

    public GameScore getGameScore() {
        return currentState.getGameScore();
    }

    public void undoTurn() {
        validateHistory();
        currentState = history.pop();
    }

    private void validateHistory() {
        if(history.isEmpty()) {
            throw new IllegalStateException("[ERROR] 무를 수 있는 턴이 없습니다.");
        }
    }

    public Map<Dot, Piece> getPieces() {
        return currentState.getPieces();
    }

    public Dynasty getCurrentDynasty() {
        return currentState.getCurrentDynasty();
    }
}
