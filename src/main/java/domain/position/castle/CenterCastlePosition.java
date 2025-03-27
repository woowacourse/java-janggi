package domain.position.castle;

import domain.direction.Direction;

import java.util.Arrays;
import java.util.List;

public class CenterCastlePosition extends CastlePosition {
    private static final List<Direction> linked = Arrays.stream(Direction.values()).toList();

    public CenterCastlePosition(final int row, final int col) {
        super(row, col, linked);
    }
}
