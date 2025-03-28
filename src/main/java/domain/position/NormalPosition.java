package domain.position;

import domain.direction.Direction;

import java.util.List;

public final class NormalPosition extends JanggiPosition {
    public NormalPosition(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isCastle() {
        return false;
    }

    @Override
    protected List<Direction> getLinkedDirections() {
        return defaultDirections;
    }
}
