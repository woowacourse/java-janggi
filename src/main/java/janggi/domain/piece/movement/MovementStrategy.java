package janggi.domain.piece.movement;

import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;

public interface MovementStrategy {

    boolean isMoveable(Pieces map, Position origin, Side side, Position destination);
}
