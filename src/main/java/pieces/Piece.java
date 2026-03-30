package pieces;

import movepolicy.MoveContext;
import position.Position;

public interface Piece {

    boolean isEmpty();

    boolean isHan();

    boolean isCho();

    boolean isSameSide(Piece other);

    boolean isPo();

    MoveContext askMoveContext(Position departure, Position destination);
}
