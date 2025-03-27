package domain.piece;

import domain.piece.path.DefaultPathValidator;
import domain.piece.path.FixedSingleMovePathFinder;
import domain.position.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final Map<TeamType, List<Direction>> DIRECTIONS;
    private static final Map<Position, List<Direction>> PALACE_MOVEMENT;

    static {
        DIRECTIONS = Map.of(
                TeamType.CHO,
                List.of(Direction.UP,
                        Direction.RIGHT,
                        Direction.LEFT)
                ,
                TeamType.HAN,
                List.of(Direction.DOWN,
                        Direction.RIGHT,
                        Direction.LEFT));
        PALACE_MOVEMENT = Map.of(
                Position.of(2, 3), List.of(Direction.RIGHT_DOWN),
                Position.of(2, 5), List.of(Direction.LEFT_DOWN),
                Position.of(1, 4), List.of(Direction.RIGHT_DOWN, Direction.LEFT_DOWN),
                Position.of(7, 3), List.of(Direction.RIGHT_UP),
                Position.of(7, 5), List.of(Direction.LEFT_UP),
                Position.of(8, 4), List.of(Direction.RIGHT_UP, Direction.LEFT_UP)
        );
    }

    public Soldier(TeamType teamType) {
        super(teamType, new FixedSingleMovePathFinder(findDirections(teamType), PALACE_MOVEMENT),
                new DefaultPathValidator());
    }

    private static List<Direction> findDirections(TeamType teamType) {
        return DIRECTIONS.get(teamType);
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }

}
