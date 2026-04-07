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

    @Override
    public boolean canMove(final Piece mover, final Position from, final Position to,
                           final Map<Position, Piece> piecesOnPath) {
        if (isNotCorrectPath(from, to)) {
            return false;
        }

        if ((!isInPalace(from) || !isInPalace(to))) {
            if (isDiagonal(from, to)) {
                return false;
            }
        }

        if (isInPalace(from) && isInPalace(to)) {
            if (isDiagonal(from, to)) {
                return canDiagonalInPalace(from, to);
            }
        }

        if (isInPalace(from) && !isInPalace(to)) {
            if (isDiagonal(from, to)) {
                return false;
            }
        }

        if (isWithdraw(from, to, mover)) {
            return false;
        }

        Piece target = piecesOnPath.get(to);
        if (target == null) {
            return true;
        }

        if (hanSoldierCanDiagonalMoveInPalace(from, to) || chuSoldierCanDiagonalMoveInPalace(from, to)) {
            return false;
        }

        return mover.isAnotherTeam(target);
    }

    private boolean chuSoldierCanDiagonalMoveInPalace(Position from, Position to) {
        if ((from.getRow() == 7 && from.getCol() == 4) || (from.getRow() == 8 && from.getCol() == 3)
                || (from.getRow() == 8 && from.getCol() == 5)) {
            if (isDiagonal(from, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean hanSoldierCanDiagonalMoveInPalace(Position from, Position to) {
        if ((from.getRow() == 2 && from.getCol() == 4) || (from.getRow() == 1 && from.getCol() == 3)
                || (from.getRow() == 1 && from.getCol() == 5)) {
            if (isDiagonal(from, to)) {
                return true;
            }
        }
        return false;
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
        if (Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getCol() - to.getCol()) > 2) {
            return true;
        }

        if ((from.getRow() == to.getRow()) && Math.abs(from.getCol() - to.getCol()) != 1) {
            return true;
        }

        if ((from.getCol() == to.getCol()) && Math.abs(from.getRow() - to.getRow()) != 1) {
            return true;
        }

        return false;
    }

    private boolean isInPalace(final Position position) {
        int row = position.getRow();
        int column = position.getCol();

        return (column >= 3 && column <= 5) && ((row >= 0 && row <= 2) || (row >= 7 && row <= 9));
    }

    private boolean canDiagonalInPalace(final Position from, final Position to) {
        if (!isDiagonal(from, to) || !isInPalace(from) || !isInPalace(to)) {
            return false;
        }

        return isCenterOfPalace(from) || isCenterOfPalace(to);
    }

    private boolean isDiagonal(final Position from, final Position to) {
        return Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getCol() - to.getCol()) == 1;
    }

    private boolean isCenterOfPalace(final Position position) {
        return position.getCol() == 4 && (position.getRow() == 1 || position.getRow() == 8);
    }
}