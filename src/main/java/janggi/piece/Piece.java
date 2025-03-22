package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    public abstract PieceSymbol getPieceSymbol();

    protected boolean isBottom() {
        return camp.isBottom();
    }

    public void validateCatch(Piece otherPiece) {
        if (camp == otherPiece.getCamp()) {
            throw new IllegalArgumentException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public void validateSelect(Camp baseCamp) {
        if (camp != baseCamp) {
            throw new IllegalArgumentException("다른 진영의 기물을 선택할 수 없습니다.");
        }
    }

    public Camp getCamp() {
        return camp;
    }
}
