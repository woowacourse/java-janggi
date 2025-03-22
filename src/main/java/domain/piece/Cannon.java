package domain.piece;

import domain.TeamType;
import domain.piece.move.UnlimitedMoveRule;
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
                Direction.RIGHT
        );
    }

    public Cannon(TeamType teamType) {
        super(teamType, new UnlimitedMoveRule(DIRECTIONS), new CannonPathValidator());
    }

    private Cannon(Cannon cannon) {
        super(cannon);
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Piece newInstance() {
        return new Cannon(this);
    }
}
