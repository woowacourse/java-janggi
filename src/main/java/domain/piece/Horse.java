package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.LinearMovement;
import domain.strategy.MovementStrategy;
import domain.strategy.PathMovement;

import java.util.List;

public class Horse extends Piece {

    private final List<List<Direction>> paths = List.of(
      List.of(Direction.UP, Direction.UP_LEFT), List.of(Direction.UP, Direction.UP_RIGHT),
      List.of(Direction.RIGHT, Direction.UP_RIGHT), List.of(Direction.RIGHT, Direction.DOWN_RIGHT),
      List.of(Direction.DOWN, Direction.DOWN_LEFT), List.of(Direction.DOWN, Direction.DOWN_RIGHT),
      List.of(Direction.LEFT, Direction.UP_LEFT), List.of(Direction.LEFT, Direction.DOWN_LEFT)
    );

    public Horse(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movementStrategy.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        return "마";
    }
}
