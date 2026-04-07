package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;
import janggi.domain.Team;

import java.util.List;

public class GungAndSaMoveStorage implements MoveStorage{
    @Override
    public boolean canMove(Position from, Position to, BoardView boardState) {
        int fromRow = from.getRowValue();
        int fromColumn = from.getColumnValue();
        int toRow = to.getRowValue();
        int toColumn = to.getColumnValue();

        if (!(3 <= fromRow && fromRow <= 5) || !(3 <= toRow && toRow <= 5)) {
            return false;
        }

        Team currentTeam = boardState.getPieceAt(from).getTeam();

        if (currentTeam == Team.HAN) {
            if (!(0 <= fromColumn && fromColumn <= 2) || !(0 <= toColumn && toColumn <= 2)) {
                return false;
            }
        }

        if (currentTeam == Team.CHO) {
            if (!(7 <= fromColumn && fromColumn <= 9) || !(7 <= toColumn && toColumn <= 9)) {
                return false;
            }
        }

        if (fromRow != toRow && fromColumn != toColumn) {
            return false;
        }

        if (fromColumn == toColumn && Math.abs(fromRow - toRow) != 1) {
            return false;
        }

        if (fromRow == toRow && Math.abs(fromColumn - toColumn) != 1) {
            return false;
        }

        return true;
    }
}
