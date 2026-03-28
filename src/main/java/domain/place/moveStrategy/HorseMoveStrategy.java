package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class HorseMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.TOP
    );

    private static final List<Direction> DIAGONAL_DIRECTIONS = List.of(
            Direction.LEFT_DOWN, Direction.LEFT_TOP, Direction.RIGHT_DOWN, Direction.RIGHT_TOP
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (board.isSameTeam(from, to)) {
            return false;
        }

        return ORTHOGONAL_DIRECTIONS.stream()
                .filter(d -> isFirstStepClear(board, from, d))
                .anyMatch(d -> isPathClear(from, to, d));
    }

    private boolean isAlignedWith(Direction straight, Direction diagonal) {
        return straight.getRow() == diagonal.getRow()
                || straight.getColumn() == diagonal.getColumn();
    }

    private boolean isFirstStepClear(BoardView board, Position from, Direction direction) {
        return from.moveIfInBounds(direction)
                .map(board::isEmpty)
                .orElse(false);
    }

    private boolean isPathClear(Position from, Position to, Direction direction) {
        return from.moveIfInBounds(direction)
                .map(step1 -> isStep2Clear(step1, to, direction))
                .orElse(false);
    }

    private boolean isStep2Clear(Position step1, Position to, Direction direction) {
        List<Direction> diagonal = DIAGONAL_DIRECTIONS.stream()
                .filter(dig -> isAlignedWith(direction, dig))
                .filter(dig -> step1.moveIfInBounds(dig).isPresent())
                .toList();

        return diagonal.stream()
                .flatMap(dig -> step1.moveIfInBounds(dig).stream())
                .anyMatch(to::equals);
    }
}
