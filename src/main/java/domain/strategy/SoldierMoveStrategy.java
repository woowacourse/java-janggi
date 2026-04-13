package domain.strategy;

import domain.Piece;
import domain.Team;
import domain.vo.Position;

import java.util.List;
import java.util.Map;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getPath(final Position from, final Position to) {
        if (isNotCorrectPath(from, to)) {
            return List.of();
        }
        return List.of(to);
    }

    public boolean canMove(final Piece mover, final Position from, final Position to,
                           final Map<Position, Piece> piecesOnPath) {
        if (isNotCorrectPath(from, to)) {
            return false;
        }
        if (isWithdraw(from, to, mover)) {
            return false;
        }
        Piece target = piecesOnPath.get(to);
        if (target == null) {
            return true;
        }
        return mover.isAnotherTeam(target);
    }

    private boolean isWithdraw(final Position from, final Position to, final Piece mover) {
        Team team = mover.getTeam();
        if (team == Team.CHU && from.getRow() - to.getRow() == 1) {
            return true;
        }

        if (team == Team.HAN && from.getRow() - to.getRow() == -1) {
            return true;
        }

        return false;
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        if (Palace.canDiagonalInPalace(from, to)
                && Math.abs(from.getRow() - to.getRow()) == 1
                && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }
        return Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getCol() - to.getCol()) != 1;
    }
}