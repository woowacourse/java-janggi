package janggi.piece.path;

import janggi.position.Position;
import java.util.List;

public interface PathCalculator {

    List<Position> calculatePath(final Position start, final Position end);
}
