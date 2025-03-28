package janggi.strategy;

import janggi.direction.PieceMovement;
import janggi.piece.Pieces;
import janggi.position.Position;

public interface MoveStrategy {

    void validatePath(Position currentPosition, Position arrivalPosition, Pieces pieces);

    PieceMovement getPieceMovement();
}
