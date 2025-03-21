package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;

public class Start implements GameState {
    private final JanggiBoard janggiBoard = new JanggiBoard();

    @Override
    public GameState start() {
        return new Run(janggiBoard, new Player());
    }

    @Override
    public GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        throw new UnsupportedOperationException("게임을 start 해야만 move 할 수 있습니다.");
    }

    @Override
    public GameState end() {
        return null;
    }
}
