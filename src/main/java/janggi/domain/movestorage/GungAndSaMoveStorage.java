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
        return !isInPalace(from, team) || !isInPalace(to, team);
    }

    private boolean isInPalace(Position position, Team team) {
        int row = position.getRowValue();
        int column = position.getColumnValue();

        if (team == Team.HAN) {
            return (3 <= row && row <= 5) && (0 <= column && column <= 2);
        }

        if (team == Team.CHO) {
            return (3 <= row && row <= 5) && (7 <= column && column <= 9);
        }

        return false;
    }

    private boolean isLegalMove(Position from, Position to) {
        int rowDiff = Math.abs(from.getRowValue() - to.getRowValue());
        int columnDiff = Math.abs(from.getColumnValue() - to.getColumnValue());

        if (rowDiff + columnDiff == 1) {
            return true;
        }

        return false;
    }
}
