package domain.piece;

import domain.TeamType;
import domain.piece.move.UnlimitedMoveRule;
import domain.piece.path.DefaultPathValidator;
import domain.position.Direction;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Direction> DIRECTIONS;

    static {
        DIRECTIONS = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.LEFT,
                Direction.RIGHT
        );
    }

    public Chariot(TeamType teamType) {
        super(teamType, new UnlimitedMoveRule(DIRECTIONS), new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }
}
