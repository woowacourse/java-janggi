package janggi.domain.piece.movement;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Pieces;

public interface MovementStrategy {

    boolean isMoveable(Pieces map, Position origin, Side side, Position destination);
}
