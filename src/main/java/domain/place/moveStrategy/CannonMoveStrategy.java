package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;
import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );
    private static final int REQUIRED_OBSTACLE_COUNT = 1;

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (board.isSameSide(from, to) || board.isCannon(to)) {
            return false;
        }

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
        Optional<Position> currentPosition = from.moveIfInBounds(direction);
        int obstacleCount = 0;

        while (currentPosition.isPresent() && !to.equals(currentPosition.get())) {
            if (!board.isEmpty(currentPosition.get())) {
                obstacleCount++;
            }

            if (board.isCannon(currentPosition.get())) {
                return false;
            }

            currentPosition = currentPosition.get().moveIfInBounds(direction);
        }
        return obstacleCount == REQUIRED_OBSTACLE_COUNT && currentPosition.isPresent();
    }

}
