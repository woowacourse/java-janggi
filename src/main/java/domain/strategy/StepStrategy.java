package domain.strategy;

import domain.board.BoardBounds;
import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;

import java.util.List;

public class StepStrategy implements Strategy {

    private final List<Direction> directions;

    public StepStrategy(List<Direction> directions) {
        this.directions = directions;
    }

    @Override
    public List<Path> getPaths(Position start, BoardBounds bounds) {
        return directions.stream()
                .map(start::nextPosition)
                .filter(bounds::contains)
                .map(pos -> new Path(List.of(pos)))
                .toList();
    }
}