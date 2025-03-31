package janggi.domain.piece;

import janggi.domain.movement.Movement;

final public class CommonValidator {

    public static void validateStraightMovement(final Position beforePosition, final Position afterPosition) {
        if (!(afterPosition.x() == beforePosition.x() || afterPosition.y() == beforePosition.y())) {
            throw new IllegalArgumentException("해당 기물은 상하좌우로만 이동할 수 있습니다.");
        }
    }

    public static void validateLinearMovement(final Position beforePosition, final Position afterPosition) {
        Movement movement = beforePosition.getMovementTo(afterPosition);
        if (movement.x() != 0 && movement.y() != 0 && movement.x() != movement.y()) {
            throw new IllegalArgumentException("해당 기물은 직선으로만 이동할 수 있습니다.");
        }
    }

    public static void validateSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        Movement movement = beforePosition.getMovementTo(afterPosition);
        if (!(Movement.isUnitMovement(movement))) {
            throw new IllegalArgumentException("해당 기물은 1칸만 이동할 수 있습니다.");
        }
    }
}
