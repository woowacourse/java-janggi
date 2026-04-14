package domain.strategy;

import domain.board.Board;
import domain.board.Team;
import domain.vo.Position;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (!from.isOneStepStraightTo(to)
                && !MoveValidator.canMoveOneStepDiagonal(from, to, board))
            return false;

        if (isWithdraw(from, to, board))
            return false;

        return board.canOccupy(from, to);
    }

    private boolean isWithdraw(final Position from, final Position to, final Board board) {
        Team team = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("이동할 기물이 존재하지 않습니다."))
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
