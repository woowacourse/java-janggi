package domain;

import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current, BoardReader board);
}
