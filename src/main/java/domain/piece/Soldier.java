package domain.piece;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(Side side, MovementStrategy movementStrategy) {
        super(side, movementStrategy);
    }

    @Override
    protected List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board) {
        return paths.stream()
                .map(Path::getDestination)
                .filter(destination -> isForwardOrSideways(current, destination))
                .filter(destination -> isValidDestination(destination, board))
                .toList();
    }

    private boolean isForwardOrSideways(Position current, Position destination) {
        int deltaY = destination.getY() - current.getY();
        if (getSide().isCho()) {
            return deltaY >= 0;
        }
        return deltaY <= 0;
    }

    @Override
    public String toString() {
        return "졸";
    }
}
