package pieces;

import movepolicy.MoveContext;
import position.Position;

public interface Piece {
    boolean isHan();

    boolean isCho();

    MoveContext askMoveContext(Position departure, Position destination);

    boolean isSameSide(Piece destinationPiece);

    boolean isPo();
}
