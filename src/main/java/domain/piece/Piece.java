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
        return new Destinations(movementStrategy.getMovablePositions(current, board, side));
    }

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

    public boolean isVital() {
        return false;
    }

    public boolean canBeBridge() {
        return true;
    }

    public boolean canBeCapturedByJump() {
        return true;
    }

    public Side getSide() {
        return side;
    }
}
