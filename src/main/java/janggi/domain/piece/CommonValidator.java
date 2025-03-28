package janggi.domain.piece;

final public class CommonValidator {

    public static void validateStraightMovement(final Position beforePosition, final Position afterPosition) {
        if (!isStraightMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("해당 기물은 직선으로만 이동할 수 있습니다.");
        }
    }

    private static boolean isStraightMovement(final Position beforePosition, final Position afterPosition) {
        return afterPosition.x() == beforePosition.x() || afterPosition.y() == beforePosition.y();
    }

    public static void validateSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        if (!isSingleStepMovement(beforePosition, afterPosition)) {
            throw new IllegalArgumentException("해당 기물은 1칸만 이동할 수 있습니다.");
        }
    }

    private static boolean isSingleStepMovement(final Position beforePosition, final Position afterPosition) {
        return Math.abs(afterPosition.x() - beforePosition.x()) + Math.abs(afterPosition.y() - beforePosition.y()) == 1;
    }
}
