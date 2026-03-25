package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ElephantMoveStrategy implements MoveStrategy {


    @Override
    public boolean canMove(Position from, Position to, Board board) {
        int currentRow = from.getRow();
        int currentCol = from.getCol();

        int targetRow = to.getRow();
        int targetCol = to.getCol();

//        if (checkStraightPath(currentCol, targetCol, currentRow, targetRow))
//            return false;

        return false;
    }
}
