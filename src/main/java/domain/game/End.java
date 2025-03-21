package domain.game;

import domain.JanggiPosition;

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
}
