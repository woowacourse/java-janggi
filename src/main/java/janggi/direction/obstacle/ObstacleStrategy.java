package janggi.direction.obstacle;

import janggi.direction.Movement;
import janggi.piece.board.Board;
import janggi.position.Position;

public interface ObstacleStrategy {

    void checkObstacle(Position currentPosition, Position arrivalPosition, Movement movement, Board board);

    boolean isObstacleJumping();
}
