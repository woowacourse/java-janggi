package domain;

import java.util.List;

public class Guard extends Piece {
    public Guard(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        return filterStandardPaths(paths, board);
    }

    @Override
    public String toString() {
        return "사";
    }
}
