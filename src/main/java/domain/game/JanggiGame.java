package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;

public class JanggiGame {
    private GameState state = new Start();

    public Map<JanggiPosition, Piece> start(Map<JanggiPosition, Piece> board) {
        state = state.start(board);
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

    public int getChoScore() {
        return state.getChoScore();
    }

    public int getHanScore() {
        return state.getHanScore();
    }
}
