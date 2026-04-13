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
        if (Palace.canDiagonalInPalace(from, to)
                && Math.abs(from.getRow() - to.getRow()) == 1
                && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }
        return Math.abs(from.getRow() - to.getRow()) + Math.abs(from.getCol() - to.getCol()) != 1;
    }

    private boolean palaceInRange(final Position position) {
        return (((0 <= position.getRow() && position.getRow() <= 2) || (7 <= position.getRow()
                && position.getRow() <= 9))
                && (3 <= position.getCol() && position.getCol() <= 5));
    }
}