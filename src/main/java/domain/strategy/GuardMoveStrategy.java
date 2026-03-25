package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class GuardMoveStrategy implements MoveStrategy{


    @Override
    public boolean canMove(Position from, Position to, Board board) {
        return false;
    }

    private boolean inRange(int row, int col) {
        return ((0 <= row && row < 3) && (3 <= col && col < 6))
                || ((7 <= row && row <10) && (3 <= col && col < 6));
    }
}
