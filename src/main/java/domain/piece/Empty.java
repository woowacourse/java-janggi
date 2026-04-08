package domain.piece;

import domain.vo.Position;
import java.util.List;

public class Empty extends Piece {

    private static final String NAME = ". ";
    private static final int SCORE = 0;

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

    @Override
    public int getScore() {
        return SCORE;
    }
}
