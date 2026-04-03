package janggi.domain.movepath;

import janggi.domain.Position;
import java.util.List;

public interface MovePathStrategy {

    boolean matches(int dx, int dy);

    List<Position> intermediatePositions(Position start, Position end);
}
