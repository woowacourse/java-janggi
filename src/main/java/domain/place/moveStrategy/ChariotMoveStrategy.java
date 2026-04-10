package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class ChariotMoveStrategy extends AbstractOrthogonalMoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        return canMoveOrthogonally(board, from, to);
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
}
