package domain.piece;

import domain.Direction;
import domain.Movement;
import domain.TeamType;
import domain.piece.path.DefaultPathValidator;
import domain.piece.path.FixedPatternPathFinder;
import java.util.List;

public class Guard extends Piece {
    private static final List<Movement> MOVEMENTS;

    static {
        MOVEMENTS = List.of(
                new Movement(List.of(Direction.UP)),
                new Movement(List.of(Direction.DOWN)),
                new Movement(List.of(Direction.RIGHT)),
                new Movement(List.of(Direction.LEFT)));
    }

    public Guard(TeamType teamType) {
        super(teamType, new FixedPatternPathFinder(MOVEMENTS), new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }

}
