package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class CannonMoveStrategy extends AbstractOrthogonalMoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );
    private static final int REQUIRED_OBSTACLE_COUNT = 1;

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (board.isCannon(to)) {
            return false;
        }

        return canMoveOrthogonally(board, from, to) || canMoveDiagonallyInPalace(board, from, to);
    }

    private boolean canMoveOrthogonally(BoardView board, Position from, Position to) {
        if (from.isNotStraightLine(to)) {
            return false;
        }

        return ORTHOGONAL_DIRECTIONS.stream()
                .filter(d -> isAlignedWithAxis(from, to, d))
                .filter(d -> isHeadingTowardsTarget(from, to, d))
                .anyMatch(d -> isPathClear(board, from, to, d));
    }

    private boolean canMoveDiagonallyInPalace(BoardView board, Position from, Position to) {
        if (!board.isInPalace(from) || !board.isInPalace(to)) {
            return false;
        }

        return board.findAvailableDirections(from).stream()
                .filter(direction -> canReachDiagonallyInPalace(board, from, to, direction))
                .anyMatch(d -> isPathClear(board, from, to, d));
    }

    private boolean isPathClear(BoardView board, Position from, Position to, Direction direction) {
        Position currentPosition = from.move(direction);
        int obstacleCount = 0;

        while (!to.equals(currentPosition)) {
            obstacleCount += countObstacle(board, currentPosition);
            if (board.isCannon(currentPosition)) {
                return false;
            }

            currentPosition = currentPosition.move(direction);
        }
        return obstacleCount == REQUIRED_OBSTACLE_COUNT;
    }

    private int countObstacle(BoardView board, Position position) {
        if (board.isEmpty(position)) {
            return 0;
        }
        return 1;
    }
}
