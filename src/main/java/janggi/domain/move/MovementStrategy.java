package janggi.domain.move;

import janggi.domain.board.BoardReader;
import janggi.domain.space.Path;
import janggi.domain.space.Position;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current, BoardReader board);
    List<Position> getMovablePositions(Position current, BoardReader board);
}
