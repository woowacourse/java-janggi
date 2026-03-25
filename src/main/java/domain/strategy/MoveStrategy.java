package domain.strategy;

import domain.Board;
import domain.vo.Position;

public interface MoveStrategy {

    boolean canMove(Position from, Position to, Board board);
}
