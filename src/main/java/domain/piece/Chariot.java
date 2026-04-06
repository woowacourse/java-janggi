package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.movement.Movement;
import java.util.List;

public class Chariot extends Piece {

    private final List<List<Direction>> paths = List.of(
        List.of(Direction.UP), List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
    );

    public Chariot(Side side, Movement movement) {
        super(side, movement);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movement.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        return "차";
    }
}
