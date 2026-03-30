package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.place.piece.PieceSymbol;
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
        if (board.isSameSide(from, to) || board.isSameSymbol(to, PieceSymbol.CANNON)) {
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
        int obstacleCount = 0;

        while (currentPosition.isPresent() && !to.equals(currentPosition.get())) {
            if (!board.isEmpty(currentPosition.get())) {
                obstacleCount++;
            }

            if (board.isSameSymbol(currentPosition.get(), PieceSymbol.CANNON)) {
                return false;
            }

            currentPosition = currentPosition.get().moveIfInBounds(direction);
        }
        return obstacleCount == REQUIRED_OBSTACLE_COUNT && currentPosition.filter(to::equals).isPresent();
    }

}
