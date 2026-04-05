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
        return ORTHOGONAL_DIRECTIONS.stream()
                .filter(d -> isFirstStepClear(board, from, d))
                .anyMatch(d -> canReachViaDiagonalPath(board, from, to, d));
    }

    private boolean isAlignedWith(Direction straight, Direction diagonal) {
        return straight.getRow() == diagonal.getRow()
                || straight.getColumn() == diagonal.getColumn();
    }

    private boolean isFirstStepClear(BoardView board, Position from, Direction direction) {
        Position next = from.move(direction);
        return board.isEmpty(next);
    }

    private boolean canReachViaDiagonalPath(BoardView board, Position from, Position to, Direction direction) {
        Position firstStep = from.move(direction);

        List<Direction> diagonals = DIAGONAL_DIRECTIONS.stream()
                .filter(d -> isAlignedWith(direction, d))
                .toList();

        return diagonals.stream()
                .anyMatch(d -> isValidElephantPath(board, firstStep, to, d));
    }

    private boolean isValidElephantPath(BoardView board, Position current, Position to, Direction direction) {
        if (!isStepInBounds(current, direction)) {
            return false;
        }

        Position step1 = current.move(direction);
        if (!board.isEmpty(step1)) {
            return false;
        }

        if (!isStepInBounds(step1, direction)) {
            return false;
        }

        Position step2 = step1.move(direction);
        return to.equals(step2);
    }

    private boolean isStepInBounds(Position current, Direction direction) {
        int nextRow = current.getRow() + direction.getRow();
        int nextCol = current.getColumn() + direction.getColumn();

        return Position.isNotOutOfBounds(nextRow, nextCol);
    }
}
