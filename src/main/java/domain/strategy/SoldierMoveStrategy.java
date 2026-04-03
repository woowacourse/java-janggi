package domain.strategy;

import domain.Board;
import domain.Team;
import domain.vo.Position;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotStraightPath(from, to))
            return false;

        if (isWithdraw(from, to, board))
            return false;

        return board.canOccupy(from, to);
    }

    private boolean isNotStraightPath(final Position from, final Position to) {
        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) == 1) {
            return false;
        }

        return true;
    }

    private boolean isWithdraw(final Position from, final Position to, final Board board) {
        Team team = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동할 기물이 존재하지 않습니다."))
                .getTeam();

        if (team == Team.CHU && from.getRow() - to.getRow() == 1) {
            return true;
        }

        if (team == Team.HAN && from.getRow() - to.getRow() == -1) {
            return true;
        }

        return false;
    }
}
