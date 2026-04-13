package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;
import janggi.domain.Team;

public class GungAndSaMoveStorage implements MoveStorage {
    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        Team team = boardView.getPieceAt(from).getTeam();

        if (isOutsidePalace(from, to, team)) {
            return false;
        }

        return isLegalMove(from, to);
    }

    private boolean isOutsidePalace(Position from, Position to, Team team) {
        return !from.isInOwnPalace(team) || !to.isInOwnPalace(team);
    }

    private boolean isLegalMove(Position from, Position to) {
        if (isStraightMove(from, to)) {
            return true;
        }

        return isDiagonalMove(from, to);
    }

    private boolean isStraightMove(Position from, Position to) {
        int rowDiff = Math.abs(from.getRowValue() - to.getRowValue());
        int colDiff = Math.abs(from.getColumnValue() - to.getColumnValue());
        return rowDiff + colDiff == 1;
    }

    private boolean isDiagonalMove(Position from, Position to) {
        return from.isOnSameDiagonal(to) && (from.isPalaceCenter() || to.isPalaceCenter());
    }
}
