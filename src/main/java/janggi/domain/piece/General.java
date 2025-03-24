package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Point;
import java.util.List;

public class General implements Piece {

    private final Dynasty dynasty;

    public General(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public List<Point> movePath(Point from, Point to) {
        return List.of();
    }

    @Override
    public boolean canMove(PiecesOnPath piecesOnPath) {
        return false;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof General;
    }

    @Override
    public boolean isDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
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
