package domain.movement.strategy;

import domain.common.Position;
import domain.movement.Path;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current);
}
