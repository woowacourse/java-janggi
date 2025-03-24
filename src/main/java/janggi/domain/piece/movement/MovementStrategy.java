package janggi.domain.piece.movement;

import janggi.domain.Side;
import janggi.domain.piece.Pieces;
import janggi.domain.piece.Position;

public interface MovementStrategy {

    boolean isMoveable(Pieces map, Position origin, Side side, Position destination);
}
