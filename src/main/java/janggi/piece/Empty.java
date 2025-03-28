package janggi.piece;

import janggi.board.Board;
import janggi.position.Movement;

public class Empty extends Piece {

    public static final Empty INSTANCE = new Empty();

    public Empty() {
        super(Camp.NONE, Type.EMPTY);
    }

    public static Piece getInstance() {
        return INSTANCE;
    }

    @Override
    public void validateMove(Movement movement, Board board) {
    }
}
