package domain.rule;

import domain.position.Path;
import domain.position.Position;

public interface PathGenerator {
    public Path calculatePath(Position src, Position dest);
}
