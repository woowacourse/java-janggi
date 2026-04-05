package domain.strategy;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import java.util.List;

public interface MovementStrategy {
    List<Path> generatePaths(Position current);
    List<Position> getMovablePositions(Position current, BoardReader board, Side side);
}
