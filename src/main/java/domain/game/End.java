package domain.game;

import domain.JanggiPosition;
import domain.Score;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class End implements GameState {
    private final Map<JanggiPosition, Piece> finalBoard;
    private final Score finalScore;

    public End() {
        this.finalBoard = new HashMap<>();
        this.finalScore = null;
    }

    public End(Map<JanggiPosition, Piece> finalBoard, Score score) {
        this.finalBoard = finalBoard;
        this.finalScore = score;
    }

    @Override
    public GameState start() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public Map<JanggiPosition, Piece> getBoard() {
        return finalBoard;
    }

    @Override
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public Score getScore() {
        return finalScore;
    }
}
