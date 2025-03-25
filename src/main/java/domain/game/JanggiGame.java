package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;

public class JanggiGame {
    private GameState state = new Start();

    public Map<JanggiPosition, Piece> start() {
        state = state.start();
        return state.getBoard();
    }

    public Map<JanggiPosition, Piece> move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        state = state.move(beforePosition, afterPosition);
        return state.getBoard();
    }

//    public Map<JanggiPosition, Piece> end() {
//        state = state.end();
//        return state.getBoard();
//    }

    public boolean isEnd() {
        return state.isEnd();
    }

    public Player getPlayer() {
        return state.getCurrentPlayer();
    }
}
