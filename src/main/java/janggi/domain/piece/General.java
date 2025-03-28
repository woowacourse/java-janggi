package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.piece.movepath.MovePath;
import janggi.domain.piece.movepath.PalaceMovePath;
import java.util.Set;

public class General extends Piece {

    public General(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            return false;
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSameType(Piece piece) {
        return piece instanceof General;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new PalaceMovePath(Direction.UP),
                new PalaceMovePath(Direction.DOWN),
                new PalaceMovePath(Direction.LEFT),
                new PalaceMovePath(Direction.RIGHT),
                new PalaceMovePath(Direction.UP_LEFT_DIAGONAL),
                new PalaceMovePath(Direction.UP_RIGHT_DIAGONAL),
                new PalaceMovePath(Direction.DOWN_LEFT_DIAGONAL),
                new PalaceMovePath(Direction.DOWN_RIGHT_DIAGONAL));
    }
}
