package janggi.moveStrategy;

import janggi.board.Board;
import janggi.piece.Team;
import janggi.position.Position;

public interface MoveStrategy {

    boolean canMove(final Position start, final Position end, final Team team, final Board board);
}
