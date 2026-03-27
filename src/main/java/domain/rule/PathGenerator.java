package domain.rule;

import domain.position.Path;
import domain.position.Position;

public interface PathGenerator {
    Path calculatePath(Position src, Position dest);
}
