package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;
import janggi.domain.Team;

public class JolMoveStorage implements MoveStorage{
    private static final int HAN_FORWARD = 1;
    private static final int CHO_FORWARD = -1;
    public static final int NEXT_TO = 1;

    @Override
    public boolean canMove(Position from, Position to, BoardView boardView) {
        Team team = boardView.getPieceAt(from).getTeam();

        if (isSidewaysMove(from, to)) {
            return true;
        }

        if (isForwardMove(from, to, team)) {
            return true;
        }

        return false;
    }

    private boolean isSidewaysMove(Position from, Position to) {
        return from.getColumnValue() == to.getColumnValue() && Math.abs(from.getRowValue() - to.getRowValue()) == NEXT_TO;
    }

    private boolean isForwardMove(Position from, Position to, Team team) {
        int columnDiff = to.getColumnValue() - from.getColumnValue();
        boolean isSameRow = from.getRowValue() == to.getRowValue();

        if (team == Team.HAN) {
            return isSameRow && columnDiff == HAN_FORWARD;
        }

        return isSameRow && columnDiff == CHO_FORWARD;
    }
}
