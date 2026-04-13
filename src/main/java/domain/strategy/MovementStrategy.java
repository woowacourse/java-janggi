package domain.strategy;

import domain.game.Position;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current);
}
