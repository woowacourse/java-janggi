package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.board.Board;

public class Finish extends Started {
    public Finish(Board board) {
        super(board);
    }

    @Override
    public PlayerTurn move(Position start, Position end){
        throw new IllegalStateException("게임 종료 상태에서는 이동할 수 없습니다.");
    }
}
