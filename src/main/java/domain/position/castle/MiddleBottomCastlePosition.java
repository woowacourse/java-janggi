package domain.position.castle;

import domain.direction.Direction;

import java.util.List;

public class MiddleBottomCastlePosition extends CastlePosition {
    private static final List<Direction> linked = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    public MiddleBottomCastlePosition(int row, int col) {
        super(row, col, linked);
    }
}
