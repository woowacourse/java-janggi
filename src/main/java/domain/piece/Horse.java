package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;

import java.util.List;

public class Horse extends Piece {

    private static final List<List<Direction>> PATHS = List.of(
      List.of(Direction.UP, Direction.UP_LEFT), List.of(Direction.UP, Direction.UP_RIGHT),
      List.of(Direction.RIGHT, Direction.UP_RIGHT), List.of(Direction.RIGHT, Direction.DOWN_RIGHT),
      List.of(Direction.DOWN, Direction.DOWN_LEFT), List.of(Direction.DOWN, Direction.DOWN_RIGHT),
      List.of(Direction.LEFT, Direction.UP_LEFT), List.of(Direction.LEFT, Direction.DOWN_LEFT)
    );

    public Horse(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position source) {
        return movementStrategy.findRoute(PATHS, source);
    }

    @Override
    public List<Position> findPathTo(Position source, Position target) {
        return movementStrategy.findPathTo(PATHS, source, target);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "马";
        }
        return "馬";
    }

    @Override
    public double getScore() {
        return 5;
    }
}
