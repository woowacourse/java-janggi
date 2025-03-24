package janggi.piece;

import janggi.position.Position;

public class Empty extends Piece {

    public Empty(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Position fromPosition, Position toPosition) {
    }

    @Override
    public Type getPieceSymbol() {
        return Type.EMPTY;
    }
}
