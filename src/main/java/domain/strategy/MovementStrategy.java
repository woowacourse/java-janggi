package domain.strategy;

import domain.Position;
import domain.board.BoardReader;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current, BoardReader board);
    List<Position> getMovablePositions(Position current, BoardReader board);
}
