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
        List<Position> validDestinations = filterValidPositions(paths, board);
        return new Destinations(validDestinations);
    }

    protected abstract List<Position> filterValidPositions(List<Path> paths, BoardReader board);

    protected List<Position> filterStandardPaths(List<Path> paths, BoardReader board) {
        return paths.stream()
                .filter(path -> isMovablePath(path, board))
                .map(Path::getDestination)
                .toList();
    }

    private boolean isMovablePath(Path path, BoardReader board) {
        return isObstaclesClear(path, board) && isValidDestination(path.getDestination(), board);
    }

    private boolean isObstaclesClear(Path path, BoardReader board) {
        return path.getObstacles().stream()
                .allMatch(board::isEmpty);
    }

    protected boolean isValidDestination(Position destination, BoardReader board) {
        return board.isEmpty(destination) || !board.getPiece(destination).isAlly(side);
    }

    public boolean isAlly(Side other) {
        return this.side.isAlly(other);
    }

    public Side getSide() {
        return side;
    }

    public boolean isGeneral() {
        return false;
    }

    public boolean isCannon() {
        return false;
    }

    public abstract PieceType getPieceType();
    public abstract String getName();
}
