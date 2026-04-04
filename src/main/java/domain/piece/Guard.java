package domain.piece;

import domain.state.Side;
import domain.coordinate.Direction;
import domain.strategy.SingleStepStrategy;

import java.util.List;

public final class Guard extends Piece {

    private static final int SCORE = 3;

    private static final List<Direction> GUARD_DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    public Guard(Side side) {
        super(
                PieceType.GUARD,
                side,
                new SingleStepStrategy(GUARD_DIRECTIONS)
        );
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
