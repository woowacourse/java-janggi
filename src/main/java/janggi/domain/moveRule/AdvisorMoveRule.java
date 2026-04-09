package janggi.domain.moveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;

public class AdvisorMoveRule implements MoveRule {

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (!board.isInsidePalace(from) || !board.isInsidePalace(to)) {
            return false;
        }
        return isStraightOneStep(from, to) || board.canMoveDiagonallyInPalace(from, to);
    }

    private boolean isStraightOneStep(Position from, Position to) {
        int rowDis = Math.abs(to.getRow() - from.getRow());
        int colDis = Math.abs(to.getCol() - from.getCol());
        return (rowDis + colDis) == 1;
    }
}
