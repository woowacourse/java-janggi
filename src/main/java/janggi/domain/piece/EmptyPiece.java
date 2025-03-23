package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Point;
import java.util.List;

public class EmptyPiece implements Piece {

    @Override
    public boolean isEmptyPiece() {
        return true;
    }

    @Override
    public List<Point> movePath(Point from, Point to) {
        throw new IllegalArgumentException("움직일 수 없습니다.");
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        return false;
    }

    @Override
    public boolean isDynasty(Dynasty dynasty) {
        return dynasty == Dynasty.EMPTY;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof EmptyPiece;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
