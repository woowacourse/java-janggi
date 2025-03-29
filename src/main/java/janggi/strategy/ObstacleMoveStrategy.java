package janggi.strategy;

import janggi.direction.Movement;
import janggi.board.Board;
import janggi.position.Position;

public interface ObstacleMoveStrategy {

    void checkObstacle(Position currentPosition, Position arrivalPosition, Movement movement, Board board);
}
