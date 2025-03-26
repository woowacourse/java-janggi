package janggi.domain.rule.move;

import janggi.domain.Palace;
import janggi.domain.Position;

public class OneStepMoveStrategy implements MoveStrategy {
    @Override
    public void validateCorrectRule(final Position departure, final Position destination) {
        if (Palace.isNotInnerSamePalace(departure, destination)) {
            checkMoveStraightOnly(departure, destination);
            return;
        }
        validateWhenContainsPalaceCenter(departure, destination);
    }

    private void validateWhenContainsPalaceCenter(final Position departure, final Position destination) {
        Palace palace = Palace.findPalaceInPosition(departure);
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);
        if (palace.isCenterOfPalace(departure) || palace.isCenterOfPalace(destination)) {
            checkMoveStraightAndDiagonal(diffRow, diffColumn);
            return;
        }
        checkMoveStraightOnly(departure, destination);
    }

    private void checkMoveStraightOnly(final Position departure, final Position destination) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);
        if (Math.abs(diffRow) + Math.abs(diffColumn) != 1) {
            throw new IllegalArgumentException("수직/수평으로 1칸만 이동 가능합니다.");
        }
    }

    private void checkMoveStraightAndDiagonal(final int diffRow, final int diffColumn) {
        if (Math.abs(diffRow) > 1 || Math.abs(diffColumn) > 1) {
            throw new IllegalArgumentException("수직/수평/대각선으로 1칸만 이동 가능합니다.");
        }
    }
}
