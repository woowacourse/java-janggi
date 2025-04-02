package janggi.piece.movement;

import janggi.position.Position;
import java.util.List;

public interface MovementRule {

    void validateMovementRule(final Position start, final Position end);
}
