package domain.piece.strategy;

import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class SingleStepMoveStrategy implements MoveStrategy {
    @Override
    public List<Position> findMovablePath(Position start, Position destination) {
        for (Direction direction : getDirections()) {
            if (!direction.isMovable(start)) {
                continue;
            }
            Position movedPosition = direction.getMovedPosition(start);
            if (movedPosition.equals(destination)) {
                return getPath(start, direction);
            }
        }
        throw new IllegalArgumentException(PieceExceptionMessage.INVALID_POSITION.getMessage());
    }

    private List<Position> getPath(Position start, Direction direction) {
        List<Position> path = new ArrayList<>();
        path.add(direction.getMovedPosition(start));
        return path;
    }

    abstract List<Direction> getDirections();
}
