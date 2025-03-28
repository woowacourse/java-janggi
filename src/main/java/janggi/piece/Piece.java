package janggi.piece;

import janggi.board.point.Point;
import java.util.Set;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract Set<Point> findRoute(Point fromPoint, Point toPoint);

    public abstract void validateMove(Point fromPoint, Point toPoint, Set<Piece> piecesOnRoute);

    protected abstract boolean canCapture(Piece otherPiece);

    public abstract PieceSymbol getPieceSymbol();

    public abstract int getPoint();

    public final void validateCatch(Piece otherPiece) {
        if (!canCapture(otherPiece)) {
            throw new IllegalArgumentException("해당 기물을 잡을 수 없습니다.");
        }
    }

    public final void validateSelect(Camp baseCamp) {
        if (camp != baseCamp) {
            throw new IllegalArgumentException("다른 진영의 기물을 선택할 수 없습니다.");
        }
    }

    public final boolean isEnemy(Piece otherPiece) {
        return camp.isEnemy(otherPiece.camp);
    }

    public final Camp getCamp() {
        return camp;
    }
}
