package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class End implements GameState {
    private final Map<JanggiPosition, Piece> finalBoard;

    public End() {
        this.finalBoard = new HashMap<>();
    }

    public End(Map<JanggiPosition, Piece> finalBoard) {
        this.finalBoard = finalBoard;
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
    public GameState end() {
        return this;
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
}
