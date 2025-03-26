package domain.piece;

import domain.position.Direction;
import domain.piece.path.DefaultPathValidator;
import domain.piece.path.DynamicPatternPathFinder;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Direction> directions;

    static {
        directions = List.of(Direction.DOWN, Direction.UP, Direction.LEFT, Direction.RIGHT);
    }

    public Chariot(TeamType teamType) {
        super(teamType, new DynamicPatternPathFinder(directions), new DefaultPathValidator());
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }


}
