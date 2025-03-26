package janggi.domain.rule.move;

import janggi.domain.Palace;
import janggi.domain.Position;
import janggi.domain.Route;

public class StraightMoveStrategy implements MoveStrategy {

    @Override
    public void validateCorrectRule(final Position departure, final Position destination) {
        if (Palace.isNotInnerSamePalace(departure, destination)) {
            canMoveStraightOnly(departure, destination);
            return;
        }
        validateInPalace(departure, destination);
    }

    private void validateInPalace(final Position departure, final Position destination) {
        Palace palace = Palace.findPalaceInPosition(departure);
        Route route = Route.of(departure, destination);
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);
        if (palace.isCenterOfPalace(destination) || route.hasOnlyCenterOfPalace(palace)) {
            canMoveStraightAndDiagonal(diffRow, diffColumn);
            return;
        }
        canMoveStraightOnly(departure, destination);
    }

    private void canMoveStraightOnly(final Position departure, final Position destination) {
        int diffRow = destination.subtractRow(departure);
        int diffColumn = destination.subtractColumn(departure);
        if (Math.min(Math.abs(diffRow), Math.abs(diffColumn)) != 0) {
            throw new IllegalArgumentException("수직/수평 방향 직선으로만 이동 가능합니다.");
        }
    }

    private void canMoveStraightAndDiagonal(final int diffRow, final int diffColumn) {
        if (Math.min(Math.abs(diffRow), Math.abs(diffColumn)) != 0 && Math.abs(diffRow) != Math.abs(diffColumn)) {
            throw new IllegalArgumentException("수직/수평/대각선 방향 직선으로만 이동 가능합니다.");
        }
    }
}
