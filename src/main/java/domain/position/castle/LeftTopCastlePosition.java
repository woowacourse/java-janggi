package domain.position.castle;

import domain.direction.Direction;

import java.util.List;

public class LeftTopCastlePosition extends CastlePosition {
    private static final List<Direction> linked = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.RIGHT_DOWN
    );

    public LeftTopCastlePosition(final int row, final int col) {
        super(row, col, linked);
    }
}
