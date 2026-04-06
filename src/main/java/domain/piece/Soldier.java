package domain.piece;

import domain.Direction;
import domain.Position;
import domain.Side;
import domain.movement.Movement;
import java.util.List;

public class Soldier extends Piece {

    private final List<List<Direction>> paths;

    public Soldier(Side side, Movement movement) {
        super(side, movement);
        if (Side.CHO == side) {
            paths = List.of(
                List.of(Direction.UP), List.of(Direction.RIGHT), List.of(Direction.LEFT)
            );
            return;
        }
        paths = List.of(
            List.of(Direction.DOWN), List.of(Direction.RIGHT), List.of(Direction.LEFT)
        );
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return movement.findRoute(paths, sourcePosition, targetPosition);
    }

    @Override
    public String getName() {
        if (isSameSide(Side.CHO)) {
            return "졸";
        }
        return "병";
    }
}
