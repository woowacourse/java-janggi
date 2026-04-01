package janggi.domain.mouveRule;

import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Position;

public interface MoveRule {
    boolean canMove(Position from, Position to, BoardView board);
}
