package domain.piece.strategy;

import domain.board.BoardState;
import domain.position.Position;

public interface MovingCondition {
    boolean canMove(BoardState boardState, Position startPosition, Position endPosition);
}
