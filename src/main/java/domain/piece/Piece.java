package domain.piece;

import domain.Position;

public interface Piece {
    boolean canMove(Position source, Position target);
}
