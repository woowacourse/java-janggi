package domain.game;

import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;

public class End implements GameState {
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
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }

    @Override
    public Boolean isEnd() {
        return true;
    }

    @Override
    public Map<JanggiPosition, Piece> getBoard() {
        return new HashMap<>();
    }

    @Override
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("게임이 종료되었습니다.");
    }
}
