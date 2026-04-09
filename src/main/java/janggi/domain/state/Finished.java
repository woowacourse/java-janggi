package janggi.domain.state;

import janggi.domain.Position;
import janggi.domain.board.Board;

public abstract class Finished extends Started {

    @Override
    public GameState move(Position from, Position to, Board board) {
        throw new IllegalStateException("게임이 종료된 상태입니다.");
    }

    @Override
    public boolean isOngoing() {
        return false;
    }

    @Override
    public void validateCamp(Position current, Board board) {
        throw new IllegalStateException("게임이 종료된 상태입니다.");
    }
}
