package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.position.Position;

public class SamePieceTypeBlockingRule implements Moveable {

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        if (pieces.isSamePieceType(start, end)) {
            return false;
        }
        return true;
    }
}
