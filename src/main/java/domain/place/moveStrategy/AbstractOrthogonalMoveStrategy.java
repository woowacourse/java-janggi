package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;

public abstract class AbstractOrthogonalMoveStrategy implements MoveStrategy {

    protected boolean isAlignedWithAxis(Position from, Position to, Direction direction) {
        if (direction.getRow() == 0) {
            return from.getRow() == to.getRow();
        }

        if (direction.getColumn() == 0) {
            return from.getColumn() == to.getColumn();
        }

        return false;
    }

    protected boolean isHeadingTowardsTarget(Position from, Position to, Direction direction) {
        int vectorSum = (to.getRow() - from.getRow()) + (to.getColumn() - from.getColumn());
        int movedVectorSum = vectorSum + direction.getRow() + direction.getColumn();

        return Math.abs(vectorSum) < Math.abs(movedVectorSum);
    }

    protected boolean canReachDiagonallyInPalace(BoardView board, Position from, Position to, Direction direction) {
        Position current = from.move(direction);

        while (board.isInPalace(current)) {
            if (current.equals(to)) {
                return true;
            }

            if (!board.isPalaceConnected(current, direction)) {
                return false;
            }

            current = current.move(direction);
        }
        return false;
    }
}
