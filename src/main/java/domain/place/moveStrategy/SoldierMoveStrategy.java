package domain.place.moveStrategy;

import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy implements MoveStrategy {

    private final List<Direction> directions;

    public SoldierMoveStrategy(Side side) {
        this.directions = initDirections(side);
    }

    private List<Direction> initDirections(Side side) {
        if (side == Side.CHO) {
            return List.of(Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        }
        return List.of(Direction.TOP, Direction.LEFT, Direction.RIGHT);
    }

    @Override
    public List<Position> getPath(Position from) {
        return directions.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .toList();
    }

    @Override
    public boolean canMove(Map<Position, Place> path, Position from, Position to) {
        return directions.stream()
                .flatMap(d -> from.moveIfInBounds(d).stream())
                .filter(p -> !path.containsKey(p))
                .anyMatch(to::equals);
    }
}
