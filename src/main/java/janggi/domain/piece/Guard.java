package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Point;
import java.util.List;

public class Guard implements Piece {

    private final Dynasty dynasty;

    public Guard(Dynasty dynasty) {
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
        if (piecesOnPath.isDestinationOfDynasty(dynasty)) {
            throw new IllegalArgumentException("목적지에 같은 나라의 기물이 있어 갈 수 없습니다.");
        }
        return piecesOnPath.isAllEmptyWithoutDestination();
    }

    @Override
    public boolean isDynasty(Dynasty dynasty) {
        return this.dynasty == dynasty;
    }

    @Override
    public boolean isSamePiece(Piece piece) {
        return piece instanceof Guard;
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
