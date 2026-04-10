package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;
import java.util.Set;

public class ChariotMoveStrategy extends AbstractOrthogonalMoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
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

    private boolean canMoveDiagonallyInPalace(BoardView board, Position from, Position to) {
        if (!board.isInPalace(from) || !board.isInPalace(to)) {
            return false;
        }

        return  board.findAvailableDirections(from).stream()
                .anyMatch(direction -> canReach(board, from, to, direction));
    }

    private boolean canReach(BoardView board, Position from, Position to, Direction direction) {
        Position current = from.move(direction);

        while (board.isInPalace(current)) {
            if (current.equals(to)) {
                return true;
            }

            if (!board.isEmpty(current)) {
                return false;
            }

            if (!board.isPalaceConnected(current, direction)) {
                return false;
            }

            current = current.move(direction);
        }

        return false;
    }
}
