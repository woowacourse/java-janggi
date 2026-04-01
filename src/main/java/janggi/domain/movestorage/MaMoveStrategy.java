package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Column;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.ArrayList;
import java.util.List;

public class MaMoveStrategy implements MoveStrategy {
    private static final int FORWARD = 2;
    private static final int DIAGONAL = 1;

    private static final int PATH_STEP_1 = 1;
    private static final int PATH_STEP_0 = 0;

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromRow = from.getRow();
        int fromCol = from.getColumn();
        int toRow = to.getRow();
        int toCol = to.getColumn();

        int diffRow = toRow - fromRow;
        int diffCol = toCol - fromCol;

        boolean isMaMove = (Math.abs(diffRow) == FORWARD && Math.abs(diffCol) == DIAGONAL) ||
                (Math.abs(diffRow) == DIAGONAL && Math.abs(diffCol) == FORWARD);

        if (!isMaMove) {
            return false;
        }

        List<Position> movementPathPositions = new ArrayList<>();

        int signRow = Integer.signum(diffRow);
        int signCol = Integer.signum(diffCol);

        if (Math.abs(diffRow) == FORWARD) {
            int step1Row = fromRow + (signRow * PATH_STEP_1);
            int step1Col = fromCol + (signCol * PATH_STEP_0);
            movementPathPositions.add(Position.of(Row.of(step1Row), Column.of(step1Col)));
        }

        if (Math.abs(diffCol) == FORWARD) {
            int step1Row = fromRow + (signRow * PATH_STEP_0);
            int step1Col = fromCol + (signCol * PATH_STEP_1);
            movementPathPositions.add(Position.of(Row.of(step1Row), Column.of(step1Col)));
        }

        for (Position position : movementPathPositions) {
            if (boardState.hasPieceAt(position)) {
                return false;
            }
        }
        return true;
    }
}
