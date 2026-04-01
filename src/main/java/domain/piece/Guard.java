package domain.piece;

import domain.board.Side;
import domain.coordinate.Direction;
import domain.strategy.SingleStepStrategy;

import java.util.List;

public final class Guard extends Piece {

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
}
