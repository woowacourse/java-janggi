package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.position.Position;

public class SameColorBlockingRule implements Moveable {

    @Override
    public boolean isMoveable(final Position start, final Position end, final Pieces pieces) {
        if (pieces.isSameColorPiece(start, end)) {
            return false;
        }
        return true;
    }
}
