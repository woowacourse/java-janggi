package janggi.domain.piece;

import janggi.domain.piece.movement.Movement;

final public class CommonValidator {

    public static void validateStraightMovement(final Position beforePosition, final Position afterPosition) {
        if (!(afterPosition.x() == beforePosition.x() || afterPosition.y() == beforePosition.y())) {
            throw new IllegalArgumentException("해당 기물은 직선으로만 이동할 수 있습니다.");
        }
    }

    public static void validateSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        Movement movement = beforePosition.getMovementTo(afterPosition);
        if (!(Math.abs(movement.x()) + Math.abs(movement.y()) == 1)) {
            throw new IllegalArgumentException("해당 기물은 1칸만 이동할 수 있습니다.");
        }
    }

    public static void validatePalaceSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        Movement movement = beforePosition.getMovementTo(afterPosition);
        if (!(Movement.isUnitMovement(movement))
                && !(movement.x() == movement.y() && Math.abs(movement.x() + movement.y()) == 2)) {
            throw new IllegalArgumentException("해당 기물은 1칸만 이동할 수 있습니다.");
        }
    }
}
