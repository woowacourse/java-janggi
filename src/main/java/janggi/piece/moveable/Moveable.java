package janggi.piece.moveable;

import janggi.piece.Pieces;
import janggi.position.Position;

public interface Moveable {

    boolean isMoveable(final Position start, final Position end, final Pieces pieces);
}
