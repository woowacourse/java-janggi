package domain.place.moveStrategy;

import domain.place.Place;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class GeneralMoveStrategy implements MoveStrategy {

    private static final List<Direction> ORTHOGONAL_DIRECTIONS = List.of(
            Direction.DOWN, Direction.LEFT, Direction.RIGHT, Direction.TOP
    );

    @Override
    public List<Position> getPath(Position from) {
        return ORTHOGONAL_DIRECTIONS.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .toList();
    }

    @Override
    public boolean canMove(Map<Position, Place> path, Position from, Position to) {
        return ORTHOGONAL_DIRECTIONS.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .filter(p -> !path.containsKey(p))
                .anyMatch(to::equals);
    }
}
