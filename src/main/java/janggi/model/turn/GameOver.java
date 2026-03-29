package janggi.model.turn;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import java.util.Map;

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
    public Map<Position, AbstractGimul> getBoard() {
        throw new IllegalStateException("게임이 이미 종료됐습니다.");
    }

    @Override
    public boolean isChoTurn() {
        throw new IllegalStateException("게임이 이미 종료됐습니다.");
    }
}
