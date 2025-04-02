package janggi.piece.movement;

import janggi.position.Position;
import java.util.Set;

public class PalaceMovementRule implements MovementRule {
    private static final Set<Position> RED_PALACE_POSITIONS = Set.of(
            new Position(4, 1), new Position(5, 1), new Position(6, 1),
            new Position(4, 2), new Position(5, 2), new Position(6, 2),
            new Position(4, 3), new Position(5, 3), new Position(6, 3)
    );
    private static final Set<Position> BLUE_PALACE_POSITIONS = Set.of(
            new Position(4, 8), new Position(5, 8), new Position(6, 8),
            new Position(4, 9), new Position(5, 9), new Position(6, 9),
            new Position(4, 10), new Position(5, 10), new Position(6, 10)
    );

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        validatePalacePosition(end);
    }

    private void validatePalacePosition(final Position end) {
        if (RED_PALACE_POSITIONS.contains(end) || BLUE_PALACE_POSITIONS.contains(end)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
