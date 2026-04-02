package domain.piece;

import domain.Destinations;
import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.List;

public abstract class Piece {
    private final Side side;
    private final MovementStrategy movementStrategy;

    protected Piece(Side side, MovementStrategy movementStrategy) {
        this.side = side;
        this.movementStrategy = movementStrategy;
    }

    public Destinations findDestinations(Position current, BoardReader board) {
        List<Path> paths = movementStrategy.generatePaths(current);
        List<Position> validDestinations = filterValidPositions(current, paths, board);
        return new Destinations(validDestinations);
    }

    protected abstract List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board);

    protected List<Position> filterStandardPaths(List<Path> paths, BoardReader board) {
        return paths.stream()
                .filter(path -> !path.isBlocked(board))
                .map(Path::getDestination)
                .filter(dest -> isCatchableOrEmpty(dest, board))
                .toList();
    }

    protected boolean isCatchableOrEmpty(Position destination, BoardReader board) {
        return board.isEmpty(destination) || !board.isAlly(destination, side);
    }

    public boolean isAlly(Side other) {
        return this.side.isAlly(other);
    }

    public boolean isGeneral() {
        return false;
    }

    public boolean isCannon() {
        return false;
    }

    public Side getSide() {
        return side;
    }
}
