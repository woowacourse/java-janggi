package domain.place.moveStrategy;

import domain.board.Board;
import domain.position.Position;

public interface MoveStrategy {
    boolean canMove(Board board, Position from, Position to);
}
