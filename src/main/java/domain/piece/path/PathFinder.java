package domain.piece.path;

import domain.Position;
import java.util.List;

public interface PathFinder {
    List<Position> findIntermediatePositions(Position from, Position to);
}
