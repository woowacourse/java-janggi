package domain.piece;

import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import domain.Position;
import domain.Side;
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
            Position destination = path.getDestination();
            if (isBackward(current, destination)) {
                continue;
            }
            if (isValidDestination(destination, board)) {
                valid.add(destination);
            }
        }
        return valid;
    }

    private boolean isBackward(Position current, Position destination) {
        if (getSide().isCho()) {
            return destination.getY() < current.getY();
        }
        return destination.getY() > current.getY();
    }

    @Override
    public String toString() {
        return "졸";
    }
}
