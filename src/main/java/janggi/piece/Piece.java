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

    public Camp getCamp() {
        return camp;
    }

    public Board getBoard() {
        return board;
    }
}
