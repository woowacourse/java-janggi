package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;

public interface MoveStrategy {
    boolean canMove(BoardView board, Position from, Position to);
}
