package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;

import java.util.List;

public class LinearStrategy implements Strategy {

    @Override
    public List<Path> getPaths(Position start) {
        List<Direction> directions = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT
        );

        return directions.stream()
                .map(direction -> new Path(start.rayPositions(direction)))
                .toList();
    }
}
