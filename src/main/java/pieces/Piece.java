package pieces;

import movepolicy.MoveContext;
import position.Position;

public interface Piece {
    boolean isHan();

    boolean isCho();

    boolean isSameSide(Piece destinationPiece);

    MoveContext askMoveContext(Position departure, Position destination);

    boolean isPo();
}
