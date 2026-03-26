package janggi.domain.mouveRule;

import janggi.domain.BoardView;
import janggi.domain.vo.Position;

public interface MoveRule {
    boolean canMove(Position from, Position to, BoardView board);
}
