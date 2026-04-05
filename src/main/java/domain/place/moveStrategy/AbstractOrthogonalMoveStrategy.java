package domain.place.moveStrategy;

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
}
