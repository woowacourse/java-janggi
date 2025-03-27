package domain.position.castle;

import domain.direction.Direction;
import domain.position.JanggiPosition;

import java.util.List;

public class CastlePosition extends JanggiPosition {
    protected CastlePosition(int row, int col, List<Direction> linked) {
        super(row, col, linked);
    }

    @Override
    public final boolean isCastle() {
        return true;
    }
}
