package domain.pathgenerator;

import domain.position.Path;
import domain.position.Position;

public interface PathGenerator {
    Path calculatePath(Position source, Position destination);

    boolean isPathPossible(Position source, Position destination);
}
