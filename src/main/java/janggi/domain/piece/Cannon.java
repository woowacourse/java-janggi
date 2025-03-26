package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.piece.movepath.EndlessMovePath;
import janggi.domain.piece.movepath.MovePath;
import java.util.Set;

public class Cannon extends Piece {

    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            return false;
        }
        if (piecesOnPath.isExistSamePiece(this)) {
            return false;
        }

        return piecesOnPath.countNotSamePieceWithoutDestination(this) == 1;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSameType(Piece piece) {
        return piece instanceof Cannon;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of(
                new EndlessMovePath(Direction.UP),
                new EndlessMovePath(Direction.DOWN),
                new EndlessMovePath(Direction.RIGHT),
                new EndlessMovePath(Direction.LEFT));
    }
}
