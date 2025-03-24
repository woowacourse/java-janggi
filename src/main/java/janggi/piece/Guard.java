package janggi.piece;

import janggi.position.Position;
import janggi.board.Board;

public final class Guard extends Piece {

    private final Board board;

    public Guard(Camp camp, Board board) {
        super(camp);
        this.board = board;
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
    }

    @Override
    public Type getPieceSymbol() {
        return Type.GUARD;
    }
}
