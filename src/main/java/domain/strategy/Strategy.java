package domain.strategy;

import domain.coordinate.Path;
import domain.coordinate.Position;
import domain.coordinate.Topology;
import java.util.List;

public interface Strategy {
    List<Path> getPaths(Position start, Topology topology);
}
