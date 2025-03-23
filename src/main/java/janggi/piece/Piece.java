package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
import janggi.exception.ErrorException;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    public abstract PieceSymbol getPieceSymbol();

    protected boolean isStart() {
        return camp.isBottom();
    }

    public void validateCatch(Piece otherPiece) {
        if (camp == otherPiece.getCamp()) {
            throw new ErrorException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public void validateSelect(Camp baseCamp) {
        if (camp != baseCamp) {
            throw new ErrorException("다른 진영의 기물을 선택할 수 없습니다.");
        }
    }

    public boolean isEmpty() {
        return getPieceSymbol() == PieceSymbol.EMPTY;
    }

    public Camp getCamp() {
        return camp;
    }
}
