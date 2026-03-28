package pieces;

import movepolicy.MoveContext;
import participant.Turn;
import position.Position;

public interface Piece {

    boolean isEmpty();

    MoveContext askMoveContext(Position departure, Position destination, Turn turn);
}
