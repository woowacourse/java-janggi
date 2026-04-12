package domain.piece;

import domain.state.Side;
import domain.coordinate.Direction;
import domain.strategy.SlidingMoveStrategy;

import java.util.List;

public final class Cannon extends Piece {

    private static final int SCORE = 7;

    private static final List<Direction> CANNON_DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    public Cannon(Side side) {
        super(
                PieceType.CANNON,
                side,
                new SlidingMoveStrategy(CANNON_DIRECTIONS)
        );
    }

    @Override
    public int getScore() {
        return SCORE;
    }
}
