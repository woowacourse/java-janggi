package domain.piece;

import domain.TeamType;
import domain.piece.move.LimitedMoveRule;
import domain.piece.move.area.PalaceOnlyConstraint;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
import java.util.List;

public class Guard extends Piece {

    private static final List<Direction> DIRECTIONS;
    private static final int GUARD_MOVE_COUNT = 1;

    static {
        DIRECTIONS = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT,
                Direction.RIGHT_UP,
                Direction.RIGHT_DOWN,
                Direction.LEFT_UP,
                Direction.LEFT_DOWN
        );
    }

    public Guard(TeamType teamType) {
        super(teamType, new LimitedMoveRule(DIRECTIONS, GUARD_MOVE_COUNT, new PalaceOnlyConstraint()),
                new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }
}
