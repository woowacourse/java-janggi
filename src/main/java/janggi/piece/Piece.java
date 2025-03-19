package janggi.piece;

import janggi.Camp;
import janggi.PieceSymbol;
import janggi.Point;
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

    protected boolean isBottom() {
        return camp.isBottom();
    }

    public void validateCatch(Piece otherPiece) {
        if (camp == otherPiece.getCamp()) {
            throw new IllegalArgumentException("같은 진영의 기물을 잡을 수 없습니다.");
        }
    }

    public Camp getCamp() {
        return camp;
    }

    public Board getBoard() {
        return board;
    }
}
