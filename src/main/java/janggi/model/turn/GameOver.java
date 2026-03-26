package janggi.model.turn;

import janggi.model.Board;
import janggi.model.position.Position;
import java.util.function.Consumer;

public class GameOver implements Turn {
    @Override
    public Turn play(Position from, Position to) {
        throw new IllegalStateException("게임 종료 후 턴을 수행할 수 없습니다.");
    }

    @Override
    public boolean isGameOver() {
        return true;
    }

    @Override
    public void accept(Consumer<Board> consumer) {
        throw new IllegalStateException("이미 게임이 종료됐습니다.");
    }
}
