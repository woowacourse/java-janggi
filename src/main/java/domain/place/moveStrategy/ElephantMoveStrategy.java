package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {

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
                .anyMatch(d -> canReachViaDiagonalPath(board, from, to, d));
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

    private boolean canReachViaDiagonalPath(BoardView board, Position from, Position to, Direction direction) {
        return from.moveIfInBounds(direction)
                .map(firstStep -> DIAGONAL_DIRECTIONS.stream()
                        .filter(d -> isAlignedWith(direction, d))
                        .anyMatch(d -> isValidElephantPath(board, firstStep, to, d)))
                .orElse(false);
    }

    private boolean isValidElephantPath(BoardView board, Position current, Position to, Direction direction) {
        return current.moveIfInBounds(direction)
                .filter(board::isEmpty)
                .flatMap(step1 -> step1.moveIfInBounds(direction))
                .map(to::equals)
                .orElse(false);
    }

}
