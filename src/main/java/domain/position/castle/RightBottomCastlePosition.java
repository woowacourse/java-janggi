package domain.position.castle;

import domain.direction.Direction;

import java.util.List;

public class RightBottomCastlePosition extends CastlePosition {
    private static final List<Direction> linked = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT,
            Direction.LEFT_UP
    );

    public RightBottomCastlePosition(int row, int col) {
        super(row, col, linked);
    }
}
