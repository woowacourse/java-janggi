package domain.piece;

import domain.board.Board;
import domain.coordination.Coordination;

public interface Piece {

    boolean isEmpty();

    Team team();

    boolean canMove(Coordination from, Coordination to, Board board);
}
