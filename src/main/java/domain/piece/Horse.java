package domain.piece;

import domain.piece.path.DefaultPathValidator;
import domain.piece.path.FixedMultiStepPathFinder;
import domain.piece.path.Movement;
import domain.position.Direction;
import java.util.List;

public class Horse extends Piece {
    private static final List<Movement> MOVEMENTS;

    static {
        MOVEMENTS = List.of(
                new Movement(List.of(Direction.UP, Direction.RIGHT_UP)),
                new Movement(List.of(Direction.UP, Direction.LEFT_UP)),
                new Movement(List.of(Direction.DOWN, Direction.RIGHT_DOWN)),
                new Movement(List.of(Direction.DOWN, Direction.LEFT_DOWN)),
                new Movement(List.of(Direction.RIGHT, Direction.RIGHT_UP)),
                new Movement(List.of(Direction.RIGHT, Direction.RIGHT_DOWN)),
                new Movement(List.of(Direction.LEFT, Direction.LEFT_UP)),
                new Movement(List.of(Direction.LEFT, Direction.LEFT_DOWN)));
    }

    public Horse(TeamType teamType) {
        super(teamType, new FixedMultiStepPathFinder(MOVEMENTS), new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }

}
