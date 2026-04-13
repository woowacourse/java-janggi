package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Position;
import janggi.domain.Team;

public class JolMoveStorage implements MoveStorage{
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

        return isPalaceDiagonalMove(from, to, team);
    }

    private boolean isSidewaysMove(Position from, Position to) {
        return from.getColumnValue() == to.getColumnValue() && Math.abs(from.getRowValue() - to.getRowValue()) == NEXT_TO;
    }

    private boolean isForwardMove(Position from, Position to, Team team) {
        int forwardDirection = getForwardDirection(team);
        int columnDiff = to.getColumnValue() - from.getColumnValue();
        boolean isSameRow = from.getRowValue() == to.getRowValue();

        return isSameRow && columnDiff == forwardDirection;
    }

    private int getForwardDirection(Team team) {
        if (team == Team.HAN) {
            return 1;
        }
        return -1;
    }

    private boolean isPalaceDiagonalMove(Position from, Position to, Team team) {
        if (!from.isInEnemyPalace(team) || !to.isInEnemyPalace(team)) {
            return false;
        }

        return isForwardDiagonal(from, to, team);
    }

    private boolean isForwardDiagonal(Position from, Position to, Team team) {
        int forwardDirection = getForwardDirection(team);
        int columnDiff = to.getColumnValue() - from.getColumnValue();
        int rowDiff = Math.abs(to.getRowValue() - from.getRowValue());

        if (columnDiff != forwardDirection || rowDiff != 1) {
            return false;
        }

        return from.isPalaceDiagonalPath(to);
    }
}
