package domain.piece;

import domain.piece.path.FixedSingleMovePathFinder;
import domain.piece.path.PalaceValidator;
import domain.position.Direction;
import domain.position.Movement;
import domain.piece.path.FixedMultiStepPathFinder;
import java.util.List;

public class Guard extends Piece {
    private static final List<Direction> DIRECTIONS;

    static {
        DIRECTIONS = List.of(
                Direction.UP,
                Direction.DOWN,
                Direction.RIGHT,
                Direction.LEFT
        );
    }

    public Guard(TeamType teamType) {
        super(teamType, new FixedSingleMovePathFinder(DIRECTIONS), new PalaceValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.GUARD;
    }

}
