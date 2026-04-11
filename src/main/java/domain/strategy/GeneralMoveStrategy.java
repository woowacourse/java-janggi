package domain.strategy;

import domain.Piece;
import domain.vo.Position;

import java.util.List;
import java.util.Map;

public class GeneralMoveStrategy implements MoveStrategy {

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

        Piece target = piecesOnPath.get(to);
        if (target == null) {
            return true;
        }
        return mover.isAnotherTeam(target);
    }

    private boolean isNotCorrectPath(final Position from, final Position to) {
        if (!palaceInRange(to)) {
            return true;
        }

        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) != 1) {
            return true;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) != 1) {
            return true;
        }

        if (hanGeneralCanDiagonalMoveInPalace(from, to) || chuGeneralCanDiagonalMoveInPalace(from, to)) {
            return true;
        }

        return false;
    }

    private boolean palaceInRange(final Position position) {
        return (((0 <= position.getRow() && position.getRow() <= 2) || (7 <= position.getRow()
                && position.getRow() <= 9))
                && (3 <= position.getCol() && position.getCol() <= 5));
    }

    private boolean chuGeneralCanDiagonalMoveInPalace(Position from, Position to) {
        if ((from.getRow() == 7 && from.getCol() == 4) || (from.getRow() == 8 && from.getCol() == 3)
                || (from.getRow() == 8 && from.getCol() == 5 || (from.getRow() == 0 && from.getCol() == 4))) {
            if (isDiagonal(from, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean hanGeneralCanDiagonalMoveInPalace(Position from, Position to) {
        if ((from.getRow() == 2 && from.getCol() == 4) || (from.getRow() == 1 && from.getCol() == 3)
                || (from.getRow() == 1 && from.getCol() == 5) || (from.getRow() == 9 && from.getCol() == 4)) {
            if (isDiagonal(from, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonal(final Position from, final Position to) {
        return Math.abs(from.getRow() - to.getRow()) == 1 && Math.abs(from.getCol() - to.getCol()) == 1;
    }
}