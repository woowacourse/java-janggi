package domain.piece;

import domain.TeamType;
import domain.piece.move.LimitedMoveRule;
import domain.piece.move.area.PalaceOnlyConstraint;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
import java.util.List;

public class King extends Piece {

    private static final List<Direction> DIRECTIONS;
    private static final int KING_MOVE_COUNT = 1;

    static {
        DIRECTIONS = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.LEFT,
                Direction.RIGHT_UP,
                Direction.LEFT_UP,
                Direction.RIGHT_DOWN,
                Direction.LEFT_DOWN
        );
    }

    public King(TeamType teamType) {
        super(teamType, new LimitedMoveRule(DIRECTIONS, KING_MOVE_COUNT, new PalaceOnlyConstraint()),
                new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
