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
        return isStepInBounds(from, direction)
                && board.isEmpty(from.move(direction));
    }

    private boolean isPathClear(Position from, Position to, Direction direction) {
        if (!isStepInBounds(from, direction)) {
            return false;
        }
        Position step1 = from.move(direction);

        return isStep2Clear(step1, to, direction);
    }

    private boolean isStep2Clear(Position step1, Position to, Direction direction) {
        List<Direction> diagonal = DIAGONAL_DIRECTIONS.stream()
                .filter(dig -> isAlignedWith(direction, dig))
                .filter(dig -> isStepInBounds(step1, dig))
                .toList();

        return diagonal.stream()
                .map(step1::move)
                .anyMatch(to::equals);
    }

    private boolean isStepInBounds(Position current, Direction direction) {
        int nextRow = current.getRow() + direction.getRow();
        int nextCol = current.getColumn() + direction.getColumn();

        return Position.isNotOutOfBounds(nextRow, nextCol);
    }
}
