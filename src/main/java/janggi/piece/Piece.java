package janggi.piece;

import janggi.board.point.Point;
import janggi.board.Board;

public abstract class Piece {

    private final Camp camp;
    private final Board board;

    public Piece(Camp camp, Board board) {
        this.camp = camp;
        this.board = board;
    }

    public abstract void validateMove(Point fromPoint, Point toPoint);

    public abstract PieceSymbol getPieceSymbol();

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

    public boolean exists() {
        return true;
    }

    public boolean isPlacedAtBottom() {
        return isBottom();
    }

    private boolean isBottom() {
        return camp.isBottom();
    }

    public Camp getCamp() {
        return camp;
    }

    public Board getBoard() {
        return board;
    }
}
