package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.ArrayList;
import java.util.List;

public class SangMoveStrategy implements MoveStrategy {
    private static final int FORWARD = 3;
    private static final int DIAGONAL = 2;

    private static final int PATH_STEP_2 = 2;
    private static final int PATH_STEP_1 = 1;
    private static final int PATH_STEP_0 = 0;

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromX = from.getRow();
        int fromY = from.getColumn();
        int toX = to.getRow();
        int toY = to.getColumn();

        int diffX = toX - fromX;
        int diffY = toY - fromY;

        boolean isSangMove = (Math.abs(diffX) == FORWARD && Math.abs(diffY) == DIAGONAL) ||
                (Math.abs(diffX) == DIAGONAL && Math.abs(diffY) == FORWARD);

        if (!isSangMove) {
            return false;
        }

        List<Position> movementPathPositions = new ArrayList<>();

        int signX = Integer.signum(diffX);
        int signY = Integer.signum(diffY);

        if (Math.abs(diffX) == FORWARD) {
            int step1X = fromX + (signX * PATH_STEP_1);
            int step1Y = fromY + (signY * PATH_STEP_0);
            movementPathPositions.add(Position.of(Row.of(step1X), Column.of(step1Y)));

            int step2X = fromX + (signX * PATH_STEP_2);
            int step2Y = fromY + (signY * PATH_STEP_1);
            movementPathPositions.add(Position.of(Row.of(step2X), Column.of(step2Y)));
        }

        if (Math.abs(diffY) == FORWARD) {
            int step1X = fromX + (signX * PATH_STEP_0);
            int step1Y = fromY + (signY * PATH_STEP_1);
            movementPathPositions.add(Position.of(Row.of(step1X), Column.of(step1Y)));

            int step2X = fromX + (signX * PATH_STEP_1);
            int step2Y = fromY + (signY * PATH_STEP_2);
            movementPathPositions.add(Position.of(Row.of(step2X), Column.of(step2Y)));
        }

        for (Position position : movementPathPositions) {
            if (boardState.hasPieceAt(position)) {
                return false;
            }
        }
        return true;
    }
}
