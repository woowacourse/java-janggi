package domain.piece;

import domain.piece.path.FixedSingleMovePathFinder;
import domain.piece.path.PalaceValidator;
import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class Guard extends Piece {
    private static final List<Direction> DIRECTIONS;
    private static final Map<Position, List<Direction>> PALACE_MOVEMENT;

    static {
        DIRECTIONS = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.LEFT
        );
        PALACE_MOVEMENT = Map.of(
                Position.of(0, 3), List.of(Direction.RIGHT_UP),
                Position.of(0, 5), List.of(Direction.LEFT_UP),
                Position.of(2, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(2, 5), List.of(Direction.LEFT_DOWN),
                Position.of(1, 4),
                List.of(Direction.RIGHT_UP, Direction.LEFT_UP, Direction.RIGHT_DOWN, Direction.LEFT_DOWN),
                Position.of(7, 3), List.of(Direction.RIGHT_UP),
                Position.of(7, 5), List.of(Direction.LEFT_UP),
                Position.of(9, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(9, 5), List.of(Direction.LEFT_DOWN),
                Position.of(8, 4),
                List.of(Direction.RIGHT_UP, Direction.LEFT_UP, Direction.RIGHT_DOWN, Direction.LEFT_DOWN)
        );
    }

    public Guard(TeamType teamType) {
        super(teamType, new FixedSingleMovePathFinder(DIRECTIONS, PALACE_MOVEMENT), new PalaceValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }

}
