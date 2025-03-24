package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.piece.movement.MovePath;
import java.util.Set;

public class EmptyPiece extends Piece {

    public EmptyPiece() {
        super(Dynasty.EMPTY);
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        return false;
    }

    @Override
    public boolean isEmptyPiece() {
        return true;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof EmptyPiece;
    }

    @Override
    protected Set<MovePath> paths() {
        return Set.of();
    }
}
