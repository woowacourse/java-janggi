package janggi.domain.piece;

import janggi.domain.piece.movepath.MovePath;
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
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    public int score() {
        return 0;
    }

    @Override
    protected boolean isKing() {
        return false;
    }

    @Override
    protected Set<MovePath> paths() {
        throw new IllegalStateException("방향이 없습니다.");
    }
}
