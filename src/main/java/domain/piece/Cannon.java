package domain.piece;

import domain.TeamType;
import domain.piece.move.UnlimitedMoveRule;
import domain.piece.move.area.FreeMoveConstraint;
import domain.piece.path.CannonPathValidator;
import domain.position.Direction;
import java.util.List;

public class Cannon extends Piece {

    private static final List<Direction> DIRECTIONS;

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

    public Cannon(TeamType teamType) {
        super(teamType, new UnlimitedMoveRule(DIRECTIONS, new FreeMoveConstraint()), new CannonPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}
