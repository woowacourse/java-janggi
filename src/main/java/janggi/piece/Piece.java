package janggi.piece;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.view.PieceSymbol;
import java.util.Set;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    public abstract void validatePathObstacles(Set<Piece> piecesOnRoute);

    public abstract Set<Point> findRoute(Point fromPoint, Point toPoint);

    public abstract PieceSymbol getPieceSymbol();

    protected boolean isJol() {
        return camp.isChu();
    }

    public void validateCatch(Piece otherPiece) {
        if (this.camp == otherPiece.camp) {
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
