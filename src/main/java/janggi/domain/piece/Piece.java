package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import janggi.domain.position.Movement;

public abstract class Piece {

    private final Camp camp;
    private final Type type;

    public Piece(Camp camp, Type type) {
        this.camp = camp;
        this.type = type;
    }

    public abstract void validateMove(Movement movement, Board board);

    public void validateCatch(Piece otherPiece) {
        if (!otherPiece.isEmpty() && camp == otherPiece.getCamp()) {
            throw new ErrorException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public boolean isOppositeCampTo(Camp baseCamp) {
        return camp != baseCamp;
    }

    public boolean isEmpty() {
        return getType() == Type.EMPTY;
    }

    public Camp getCamp() {
        return camp;
    }

    public Type getType() {
        return type;
    }
}
