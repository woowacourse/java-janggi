package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;

import java.util.List;
import java.util.Optional;

public class StepStrategy implements Strategy {

    private final List<Direction> directions;

    public StepStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> getPaths(Position start) {
        return directions.stream()
                .map(start::tryNextPosition)
                .flatMap(Optional::stream)
                .map(pos -> new Path(List.of(pos)))
                .toList();
    }
}