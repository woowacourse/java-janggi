package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.strategy.MovementStrategy;
import java.util.List;

public class Chariot extends Piece {

    private final List<List<Direction>> paths = List.of(
        List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movementStrategy.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        return "차";
    }
}
