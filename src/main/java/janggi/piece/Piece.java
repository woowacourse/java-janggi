package janggi.piece;

import janggi.board.Board;
import janggi.board.point.Point;

public abstract class Piece {

    private final Camp camp;
    private final Board board;

    public Piece(Camp camp, Board board) {
        this.camp = camp;
        this.board = board;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    protected abstract boolean canCapture(Piece otherPiece);

    public abstract PieceSymbol getPieceSymbol();

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

    public final Camp getCamp() {
        return camp;
    }

    public final Board getBoard() {
        return board;
    }
}
