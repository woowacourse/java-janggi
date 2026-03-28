package pieces;

import movepolicy.MoveContext;
import position.Position;

public interface Piece {

    boolean isEmpty();

    MoveContext askMoveContext(Position departure, Position destination);
}
