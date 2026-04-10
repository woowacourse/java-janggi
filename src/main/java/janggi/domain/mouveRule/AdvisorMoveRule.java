package janggi.domain.mouveRule;

import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Path;
import janggi.domain.vo.position.Position;

public class AdvisorMoveRule implements MoveRule {

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (Path.countOfPositionBetween(from, to) != 1) {
            return false;
        }

        return board.canInnerGo(from, to);
    }
}
