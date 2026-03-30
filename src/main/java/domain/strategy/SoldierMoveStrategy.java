package domain.strategy;

import domain.Board;
import domain.Team;
import domain.vo.Position;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotCorrectPath(from, to))
            return false;

        if (isWithdraw(from, to, board))
            return false;

        if (board.isAnotherTeam(from, to)) {
            return true;
        }

        return false;
    }

    private boolean isWithdraw(final Position from, final Position to, final Board board) {
        Team team = board.findPieceByPosition(from).get().getTeam();
        if (team == Team.CHU && from.getRow() - to.getRow() == 1) {
            return true;
        }

        if (team == Team.HAN && from.getRow() - to.getRow() == -1) {
            return true;
        }

        return false;
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) == 1) {
            return false;
        }

        return false;
    }
}
