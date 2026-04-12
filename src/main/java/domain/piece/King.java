package domain.piece;

import domain.state.Side;
import domain.coordinate.Direction;
import domain.strategy.SingleStepStrategy;

import java.util.List;

public final class King extends Piece {

    private static final int SCORE = 0;

    private static final List<Direction> KING_DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    public King(Side side) {
        super(
                PieceType.KING,
                side,
                new SingleStepStrategy(KING_DIRECTIONS)
        );
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
