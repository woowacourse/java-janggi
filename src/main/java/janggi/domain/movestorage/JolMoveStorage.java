package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;
import janggi.domain.Team;

import java.util.List;

public class JolMoveStorage implements MoveStorage{
    private static final int HAN_FORWARD = 1;
    private static final int CHO_FORWARD = -1;
    public static final int NEXT_TO = 1;

    @Override
    public boolean canMove(Position from, Position to, BoardView boardState) {
        int fromRow = from.getRowValue();
        int fromColumn = from.getColumnValue();
        int toRow = to.getRowValue();
        int toColumn = to.getColumnValue();

        if (Math.abs(fromRow - toRow) == NEXT_TO && fromColumn == toColumn) {
            return true;
        }

        Team currentTeam = boardState.getPieceAt(from).getTeam();

        if (currentTeam == Team.HAN) {
            if (toColumn - fromColumn == HAN_FORWARD && fromRow == toRow) {
                return true;
            }
        }

        if (currentTeam == Team.CHO) {
            if (toColumn - fromColumn == CHO_FORWARD && fromRow == toRow) {
                return true;
            }
        }
        
        return false;
    }
}
