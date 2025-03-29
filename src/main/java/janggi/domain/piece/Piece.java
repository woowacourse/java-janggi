package janggi.domain.piece;

import janggi.domain.piece.movepath.MovePath;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected final Dynasty dynasty;

    public Piece(Dynasty dynasty) {
        this.dynasty = dynasty;
    }

    public final List<Point> movePath(Point from, Point to) {
        MovePath movePath = paths().stream()
                .filter(each -> each.canMove(from, to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 목적지입니다."));

        return movePath.movePoints(from, to);
    }

    public boolean isDynastyKing(Dynasty dynasty) {
        return isDynasty(dynasty) && isKing();
    }

    public final boolean isDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    public boolean isSameType(Piece piece) {
        return piece.pieceType() == pieceType();
    }

    public Dynasty getDynasty() {
        return dynasty;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Piece piece = (Piece) o;
        return dynasty == piece.dynasty;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dynasty);
    }

    abstract public boolean canMove(PiecesOnPath piecesOnPath);

    abstract public boolean isEmptyPiece();

    abstract public PieceType pieceType();

    abstract public int score();

    abstract protected boolean isKing();

    abstract protected Set<MovePath> paths();
}

