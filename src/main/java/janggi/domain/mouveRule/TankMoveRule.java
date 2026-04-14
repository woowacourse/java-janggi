package janggi.domain.mouveRule;

import janggi.domain.Direction;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Position;

public class TankMoveRule implements MoveRule {
    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        if (!(board.isOnDiagonalPath(from, to) || from.isStraightLine(to))) {
            return false;
        }

        return isNotBlocked(from, to, board);
    }

    private boolean isNotBlocked(Position from, Position to, BoardView board) {
        Direction direction = Direction.between(from, to);
        Position pathPosition = from;

        while (!pathPosition.equals(to)) {
            pathPosition = pathPosition.nextPosition(direction);

            if (!board.isEmptyPosition(pathPosition)) {
                return false;
            }
        }

        return true;
    }
}
