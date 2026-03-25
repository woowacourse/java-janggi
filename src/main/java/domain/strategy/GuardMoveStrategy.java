package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class GuardMoveStrategy implements MoveStrategy{


    @Override
    public boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
