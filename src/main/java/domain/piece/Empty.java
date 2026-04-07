package domain.piece;

import domain.Position;
import java.util.List;

public class Empty extends Piece {

    private static final String NAME = ".";

    public Empty() {
        super(null, null, null);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        throw new IllegalArgumentException(INVALID_TARGET_POSITION);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
