package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;
import service.JanggiService;

public class JanggiGame {
    private final JanggiService janggiService;
    private GameState state;

    public JanggiGame(JanggiService janggiService) {
        this.janggiService = janggiService;
        state = new Start(janggiService);
    }

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

    public int getChoScore() {
        return state.getChoScore();
    }

    public int getHanScore() {
        return state.getHanScore();
    }
}
