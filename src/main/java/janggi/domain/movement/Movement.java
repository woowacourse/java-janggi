package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;

public interface Movement {

    boolean canMove(Position from);

    boolean canCatchAnyOnPath(Piece me, Position from, BoardMediator boardMediator);

    boolean isBlocked(Position from, BoardMediator boardMediator);

    List<Position> calculatePath(Position from, Piece me, BoardMediator boardMediator);
}
