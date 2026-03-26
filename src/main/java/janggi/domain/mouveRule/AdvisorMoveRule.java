package janggi.domain.mouveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;

public class AdvisorMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        int rowDis = Math.abs(to.getRow() - from.getRow());
        int colDis = Math.abs(to.getCol() - from.getCol());
        return rowDis <= 1 && colDis <= 1;
    }
}//궁성 제한이니 일단 1칸 이동만
