package domain.strategy;

import domain.Position;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current);
}
