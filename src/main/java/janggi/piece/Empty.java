package janggi.piece;

import janggi.position.Movement;

public class Empty extends Piece {

    public static final Empty INSTANCE = new Empty();

    public Empty() {
        super(Camp.NONE);
    }

    @Override
    public void validateMove(Movement movement) {
    }

    @Override
    public Type getPieceSymbol() {
        return Type.EMPTY;
    }
}
