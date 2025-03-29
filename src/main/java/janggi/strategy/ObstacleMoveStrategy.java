package janggi.strategy;

import janggi.direction.Movement;
import janggi.piece.Board;
import janggi.position.Position;

public interface ObstacleMoveStrategy {

    void checkObstacle(Position currentPosition, Position arrivalPosition, Movement movement, Board board);
}
