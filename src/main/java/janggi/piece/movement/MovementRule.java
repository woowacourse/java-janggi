package janggi.piece.movement;

import janggi.position.Position;
import java.util.List;

public interface MovementRule {

    List<Position> calculatePath(final Position start, final Position end);

    void validateMovementRule(final Position start, final Position end);
}
