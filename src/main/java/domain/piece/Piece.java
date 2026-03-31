package domain.piece;

import domain.Destinations;
import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import domain.strategy.MovementStrategy;
import domain.strategy.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final Side side;
    private final MovementStrategy movementStrategy;

    protected Piece(Side side, MovementStrategy movementStrategy) {
        this.side = side;
        this.movementStrategy = movementStrategy;
    }

    public Destinations findMovablePositions(Position current, BoardReader board) {
        List<Path> paths = movementStrategy.generatePaths(current);
        List<Position> validDestinations = filterValidPositions(current, paths, board);
        return new Destinations(validDestinations);
    }

    protected abstract List<Position> filterValidPositions(Position current, List<Path> paths, BoardReader board);

    protected List<Position> filterStandardPaths(List<Path> paths, BoardReader board) {
        List<Position> valid = new ArrayList<>();
        for (Path path : paths) {
            if (isObstaclesClear(path, board) && isValidDestination(path.getDestination(), board)) {
                valid.add(path.getDestination());
            }
        }
        return valid;
    }

    private boolean isObstaclesClear(Path path, BoardReader board) {
        for (Position obstacle : path.getObstacles()) {
            if (!board.isEmpty(obstacle)) {
                return false;
            }
        }
        return true;
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
}
