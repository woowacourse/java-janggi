package pieces;

import movepolicy.MoveContext;
import position.Position;

public interface Piece {
    MoveContext askMoveContext(Position departure, Position destination);
}
