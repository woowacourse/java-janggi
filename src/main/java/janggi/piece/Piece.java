package janggi.piece;

import janggi.exception.ErrorException;
import janggi.position.Movement;

public abstract class Piece {

    private final Camp camp;

    public Piece(Camp camp) {
        this.camp = camp;
    }

    public abstract void validateMove(Movement movement);

    public abstract Type getPieceSymbol();

    public void validateCatch(Piece otherPiece) {
        if (!otherPiece.isEmpty() && camp == otherPiece.getCamp()) {
            throw new ErrorException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public boolean isOppositeCampTo(Camp baseCamp) {
        return camp != baseCamp;
    }

    public boolean isEmpty() {
        return getPieceSymbol() == Type.EMPTY;
    }

    public Camp getCamp() {
        return camp;
    }
}
