package janggi.strategy;

import janggi.direction.PieceMovement;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Set;

public interface MoveStrategy {

    void validatePath(Position currentPosition, Position arrivalPosition, Set<Piece> pieces);

    PieceMovement getPieceMovement();
}
