package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;
import java.util.Optional;

public class ChariotMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (board.isSameSide(from, to)) {
            return false;
        }

        if (from.isNotStrategyLine(to)) {
            return false;
        }

        return ORTHOGONAL_DIRECTIONS.stream()
                .anyMatch(d -> isPathClear(board, from, to, d));
    }

    private boolean isPathClear(BoardView board, Position from, Position to, Direction direction) {
        Optional<Position> currentPosition = from.moveIfInBounds(direction);

        while (currentPosition.isPresent() && !to.equals(currentPosition.get())) {
            if (!board.isEmpty(currentPosition.get())) {
                return false;
            }

            currentPosition = currentPosition.get().moveIfInBounds(direction);
        }

        return currentPosition.filter(to::equals).isPresent();
    }
}
