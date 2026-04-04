package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import janggi.domain.piece.Piece;
import java.util.List;

public interface Movement {

    boolean canMove(Position from);

    boolean canCatch(Piece me, Position from, BoardMediator boardMediator);

    boolean isValid(Position from, BoardMediator boardMediator);

    boolean isBlocked(Position from, BoardMediator boardMediator);

    List<Position> calculateTraces(Position from, Piece me, BoardMediator boardMediator);
}
