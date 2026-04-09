package domain.strategy;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;

import java.util.List;
import java.util.Optional;

public class StepStrategy implements Strategy {

    @Override
    public List<Path> getPaths(Position start, Topology topology) {
        return topology.getDirections(start).stream()
                .map(start::tryNextPosition)
                .flatMap(Optional::stream)
                .map(pos -> new Path(List.of(pos)))
                .toList();
    }
}