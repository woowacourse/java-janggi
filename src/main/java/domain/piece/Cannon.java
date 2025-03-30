package domain.piece;

import domain.piece.path.CannonPathValidator;
import domain.piece.path.DynamicPatternPathFinder;
import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {
    private static final Map<Position, List<Direction>> PALACE_MOVEMENT;
    private static final List<Direction> DIRECTIONS;

    static {
        DIRECTIONS = List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT);
        PALACE_MOVEMENT = Map.of(
                Position.of(0, 3), List.of(Direction.RIGHT_UP),
                Position.of(0, 5), List.of(Direction.LEFT_UP),
                Position.of(2, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(2, 5), List.of(Direction.LEFT_DOWN),
                Position.of(7, 3), List.of(Direction.RIGHT_UP),
                Position.of(7, 5), List.of(Direction.LEFT_UP),
                Position.of(9, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(9, 5), List.of(Direction.LEFT_DOWN)
        );
    }

    public Cannon(TeamType teamType) {
        super(teamType, new DynamicPatternPathFinder(DIRECTIONS, PALACE_MOVEMENT), new CannonPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

}
