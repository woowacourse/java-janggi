package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {


        return false;
    }
}
