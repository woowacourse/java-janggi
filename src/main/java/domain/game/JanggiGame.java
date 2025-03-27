package domain.game;

import domain.JanggiPosition;
import domain.Score;
import domain.piece.Piece;
import java.util.Map;

public class JanggiGame {
    private GameState state = new Start();

    public Map<JanggiPosition, Piece> start() {
        state = state.start();
        return state.getBoard();
    }

    public Map<JanggiPosition, Piece> move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        state = state.playSingleTurn(beforePosition, afterPosition);
        return state.getBoard();
    }

    public boolean isEnd() {
        return state.isEnd();
    }

    public Player getPlayer() {
        return state.getCurrentPlayer();
    }

    public Score getScore() {
        return state.getScore();
    }
}
