package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (from.isNotStrategyLine(to)) {
            return false;
        }

        return ORTHOGONAL_DIRECTIONS.stream()
                .filter(d -> isAlignedWithAxis(from, to, d))
                .filter(d -> isHeadingTowardsTarget(from, to, d))
                .anyMatch(d -> isPathClear(board, from, to, d));
    }

    private boolean isAlignedWithAxis(Position from, Position to, Direction direction) {
        if (direction.getRow() == 0) {
            return from.getRow() == to.getRow();
        }

        if (direction.getColumn() == 0) {
            return from.getColumn() == to.getColumn();
        }

        return false;
    }

    private boolean isHeadingTowardsTarget(Position from, Position to, Direction direction) {
        int vectorSum = (to.getRow() - from.getRow()) + (to.getColumn() - from.getColumn());
        int movedVectorSum = vectorSum + direction.getRow() + direction.getColumn();

        return Math.abs(vectorSum) < Math.abs(movedVectorSum);
    }

    private boolean isPathClear(BoardView board, Position from, Position to, Direction direction) {
        Position currentPosition = from.move(direction);

        while (!to.equals(currentPosition)) {

            if (!board.isEmpty(currentPosition)) {
                return false;
            }
            currentPosition = currentPosition.move(direction);
        }

        return true;
    }
}
