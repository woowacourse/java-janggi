package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public interface MoveStrategy {

    boolean canMove(final Position from, final Position to, final Board board);
}
