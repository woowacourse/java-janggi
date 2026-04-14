package janggi.domain.state;

import janggi.domain.Position;
import janggi.domain.board.Board;

public abstract class Finished implements GameState {

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

    @Override
    public GameState giveUp() {
        throw new IllegalStateException("이미 종료된 게임은 기권할 수 없습니다.");
    }

    @Override
    public GameState draw() {
        throw new IllegalStateException("이미 종료된 게임은 무승부 요청을 할 수 없습니다.");
    }
}
