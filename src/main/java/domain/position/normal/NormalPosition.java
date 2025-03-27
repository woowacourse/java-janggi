package domain.position.normal;

import domain.direction.Direction;
import domain.position.JanggiPosition;

import java.util.List;

public final class NormalPosition extends JanggiPosition {
    private static final List<Direction> linked = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.LEFT,
            Direction.RIGHT
    );

    public NormalPosition(int row, int col) {
        super(row, col, linked);
    }

    @Override
    public boolean isCastle() {
        return false;
    }
}
