package domain.piece.strategy;

import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class MultipleStepMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        for (List<Direction> directions : getDirections()) {
            if (isValidPath(directions, start, destination)) {
                return getPath(start, directions);
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }

    private List<Position> getPath(Position start, List<Direction> directions) {
        List<Position> path = new ArrayList<>();
        Position current = start;
        for (Direction direction : directions) {
            current = direction.getMovedPosition(current);
            path.add(current);
        }
        return path;
    }

    private boolean isValidPath(List<Direction> directions, Position start, Position destination) {
        Position current = start;
        for (Direction step : directions) {
            if (!step.isMovable(current)) {
                return false;
            }
            current = step.getMovedPosition(current);
        }
        return current.equals(destination);
    }

    abstract List<List<Direction>> getDirections();
}
