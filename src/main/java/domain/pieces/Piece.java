package domain.pieces;

import domain.movepolicy.MoveContext;
import domain.position.Position;

public interface Piece {

    boolean isEmpty();

    boolean isSameSide(Piece other);

    PieceType getType();

    Side getSide();

    MoveContext askMoveContext(Position departure, Position destination);
}
