package domain.piece;

import domain.position.Direction;
import domain.piece.path.CannonPathValidator;
import domain.piece.path.DynamicPatternPathFinder;
import java.util.List;

public class Cannon extends Piece {
    private static final List<Direction> directions;

    static {
        directions = List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT);
    }

    public Cannon(TeamType teamType) {
        super(teamType, new DynamicPatternPathFinder(directions), new CannonPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

}
