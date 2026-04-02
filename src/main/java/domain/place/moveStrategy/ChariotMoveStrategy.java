package domain.place.moveStrategy;

import domain.place.Empty;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChariotMoveStrategy implements MoveStrategy {
    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.TOP, Direction.DOWN, Direction.LEFT, Direction.RIGHT
    );

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
    public boolean canMove(Map<Position, Place> board, Position from, Position to, Side fromSide) {
        Place toPlace = board.getOrDefault(to, new Empty());
        if (toPlace.hasSide(fromSide)) {
            return false;
        }
        return ORTHOGONAL_DIRECTIONS.stream()
                .anyMatch(direction -> isPathClear(board, from, to, direction));
    }

    private boolean isPathClear(Map<Position, Place> board,
                                Position from,
                                Position to,
                                Direction direction) {
        Optional<Position> current = from.moveIfInBounds(direction);

        while (isNotAtDestination(current, to)) {
            Position pos = current.get();
            Place place = board.getOrDefault(pos, new Empty());
            if (!place.isEmpty()) {
                return false;
            }

            current = current.get().moveIfInBounds(direction);
        }

        return isAtDestination(current, to);
    }

    private boolean isAtDestination(Optional<Position> current, Position to){
        return current.isPresent() && current.get().equals(to);
    }

    private boolean isNotAtDestination(Optional<Position> current, Position to){
        return current.isPresent() && !current.get().equals(to);
    }

}
