package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class End implements GameState {
    private final Map<JanggiPosition, Piece> finalBoard;
    private final int choScore;
    private final int hanScore;

    public End() {
        this.finalBoard = new HashMap<>();
        this.choScore = 0;
        this.hanScore = 0;
    }

    public End(Map<JanggiPosition, Piece> finalBoard, int choScore, int hanScore) {
        this.finalBoard = finalBoard;
        this.choScore = choScore;
        this.hanScore = hanScore;
    }

    @Override
    public GameState start() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public GameState playSingleTurn(JanggiPosition beforePosition, JanggiPosition afterPosition) {
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
    public int getChoScore() {
        return choScore;
    }

    @Override
    public int getHanScore() {
        return hanScore;
    }
}
