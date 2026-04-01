package janggi.domain.mouveRule;

import janggi.domain.BoardView;
import janggi.domain.Palace;
import janggi.domain.vo.Position;

public class PalaceMoveRule implements MoveRule {

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (!Palace.isInsidePalace(from) || !Palace.isInsidePalace(to)) {
            return false;
        }
        return isStraightOneStep(from, to) || Palace.canMoveDiagonally(from, to);
    }

    private boolean isStraightOneStep(Position from, Position to) {
        int rowDis = Math.abs(to.getRow() - from.getRow());
        int colDis = Math.abs(to.getCol() - from.getCol());
        return (rowDis + colDis) == 1;
    }
}
