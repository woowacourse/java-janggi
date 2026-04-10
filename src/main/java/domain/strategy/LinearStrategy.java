package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearStrategy implements Strategy {

    @Override
    public List<Path> getPaths(Position start, Topology topology) {
        return topology.getDirections(start).stream()
                .map(direction -> new Path(rayPositions(start, direction, topology)))
                .toList();
    }

    private List<Position> rayPositions(Position start, Direction direction, Topology topology) {
        List<Position> positions = new ArrayList<>();
        Position current = start;
        Optional<Position> next = current.tryNextPosition(direction);
        while (next.isPresent()) {
            positions.add(next.get());
            current = next.get();
            if (!topology.getDirections(current).contains(direction)) {
                break;
            }
            next = current.tryNextPosition(direction);
        }
        return positions;
    }
}