package domain.game;

import dao.JanggiDao;
import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;

public class JanggiGame {
    private GameState state = new Start();
    private final JanggiDao janggiDao = new JanggiDao();

    public JanggiGame() {
        janggiDao.initializeJanggiBoard();
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
