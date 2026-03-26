package janggi.domain.movement;

import janggi.domain.Position;
import java.util.List;

public interface Movement {
    boolean canReach(Position from);
    Position calculateDestination(Position from);
    List<Position> calculateTraces(Position from);
}
