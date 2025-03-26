package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movepath.FiniteMovePath;
import janggi.domain.piece.movepath.MovePath;
import java.util.Set;

public class ChuSoldier extends Piece {

    public ChuSoldier() {
        super(Dynasty.CHU);
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            return false;
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isSameType(Piece piece) {
        return piece instanceof ChuSoldier;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new FiniteMovePath(Direction.UP),
                new FiniteMovePath(Direction.LEFT),
                new FiniteMovePath(Direction.RIGHT)
        );
    }
}
