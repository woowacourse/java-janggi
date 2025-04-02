package janggi.direction.move;

import janggi.direction.Movement;
import janggi.direction.PieceType;
import janggi.position.Position;

public interface MoveStrategy {

    Movement move(Position currentPosition, Position arrivalPosition, PieceType pieceType);
}
