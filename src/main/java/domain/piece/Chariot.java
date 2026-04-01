package domain.piece;

import domain.board.Side;
import domain.coordinate.Direction;
import domain.strategy.SlidingMoveStrategy;

import java.util.List;

public final class Chariot extends Piece {

    private static final List<Direction> CHARIOT_DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    public Chariot(Side side) {
        super(
                PieceType.CHARIOT,
                side,
                new SlidingMoveStrategy(CHARIOT_DIRECTIONS)
        );
    }
}
