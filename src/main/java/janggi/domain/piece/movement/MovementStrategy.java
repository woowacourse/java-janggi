package janggi.domain.piece.movement;

import janggi.domain.piece.pieces.PiecesView;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;

public interface MovementStrategy {

    boolean isMoveable(PiecesView map, Position origin, Side side, Position destination);
}
