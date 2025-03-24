package janggi.piece;

import janggi.position.Position;

public class Empty extends Piece {

    public static final Empty INSTANCE = new Empty();

    public Empty() {
        super(Camp.NONE);
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
    }

    @Override
    public Type getPieceSymbol() {
        return Type.EMPTY;
    }
}
