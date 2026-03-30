package domain;

import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        List<Position> valid = new ArrayList<>();
        for (Path path : paths) {
            Position dest = path.getDestination();
            if (isBackward(current, dest)) {
                continue;
            }
            if (isValidDestination(dest, board)) {
                valid.add(dest);
            }
        }
        return valid;
    }

    private boolean isBackward(Position current, Position dest) {
        if (getSide().isCho()) {
            return dest.getY() < current.getY();
        }
        return dest.getY() > current.getY();
    }

    @Override
    public String toString() {
        return "졸";
    }
}
