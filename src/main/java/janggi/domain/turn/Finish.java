package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;

public class Finish extends Started {
    public Finish(Board board) {
        super(board, Side.EMPTY);
    }

    @Override
    public PlayerTurn move(Position start, Position end){
        throw new IllegalStateException("게임 종료 상태에서는 이동할 수 없습니다.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
