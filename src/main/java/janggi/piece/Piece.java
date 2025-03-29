package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import java.util.Set;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Point from, Point to);

    public abstract void validateRouteObstacles(Set<Piece> piecesOnRoute);

    public abstract Set<Point> findRoute(Point from, Point to);

    public abstract PieceCategory getPieceCategory();

    public void validatePalaceMove(Point from, Point to) {
        validateMove(from, to);
    }

    public Set<Point> findPalaceRoute(Point from, Point to) {
        return findRoute(from, to);
    }

    protected boolean isSameCamp(Camp other) {
        return this.camp == other;
    }

    public void validateCatch(Piece targetPiece) {
        if (this.camp == targetPiece.camp) {
            throw new IllegalArgumentException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public void validateSelect(Camp baseCamp) {
        if (this.camp != baseCamp) {
            throw new IllegalArgumentException("다른 진영의 기물을 선택할 수 없습니다.");
        }
    }

    public Camp getCamp() {
        return camp;
    }
}
