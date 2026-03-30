package domain.place.moveStrategy;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.PieceSymbol;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.TOP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    );
    private static final int REQUIRED_OBSTACLE_COUNT = 1;

    @Override
    public List<Position> getPath(Position from) {
        List<Position> result = new ArrayList<>();
        ORTHOGONAL_DIRECTIONS.forEach(direction -> collectLinePositions(result, from, direction));
        return result;
    }

    private void collectLinePositions(List<Position> result, Position from, Direction direction) {
        Optional<Position> current = from.moveIfInBounds(direction);

        while (current.isPresent()) {
            Position pos = current.get();
            result.add(pos);

            current = pos.moveIfInBounds(direction);
        }
    }

    @Override
    public boolean canMove(Map<Position, Place> board, Position from, Position to) {
        if (isTargetCannon(board, to)) {
            return false;
        }

        return ORTHOGONAL_DIRECTIONS.stream()
                .anyMatch(direction -> isPathClear(board, from, to, direction));
    }

    private boolean isPathClear(Map<Position, Place> board,
                                Position from,
                                Position to,
                                Direction direction) {
        int obstacleCount = 0;
        Optional<Position> current = from.moveIfInBounds(direction);

        while (canContinue(current, to, obstacleCount)) {
            Position pos = current.get();
            Place place = board.getOrDefault(pos, new Empty());

            if (place.isSameSymbol(PieceSymbol.CANNON)) {
                return false;
            }
            if (!place.isEmpty()) {
                obstacleCount++;
            }

            current = pos.moveIfInBounds(direction);
        }

        return isArrived(current, to) && obstacleCount == REQUIRED_OBSTACLE_COUNT;
    }

    private boolean canContinue(Optional<Position> current, Position to, int count) {
        return current.isPresent()
                && !current.get().equals(to)
                && count <= REQUIRED_OBSTACLE_COUNT;
    }

    private boolean isArrived(Optional<Position> current, Position to) {
        return current.filter(to::equals).isPresent();
    }

    private boolean isTargetCannon(Map<Position, Place> board, Position to) {
        return board.get(to).isSameSymbol(PieceSymbol.CANNON);
    }

}