package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.PathMovement;
import java.util.List;

public class Elephant extends Piece {

    private final List<List<Direction>> paths = List.of(
        List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT),
        List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
        List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT),
        List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
        List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
        List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
        List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT),
        List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT)
    );

    public Elephant(Side side) {
        super(side, new PathMovement());
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movementStrategy.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        return "상";
    }
}
