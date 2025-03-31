package domain.direction;

import domain.path.Path;
import domain.position.ChessPosition;

import java.util.ArrayList;
import java.util.List;

public class Directions {
    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean canApplyFrom(final ChessPosition startPosition) {
        ChessPosition currentPosition = startPosition;
        for (Direction direction : directions) {
            if (!currentPosition.canMove(direction)) {
                return false;
            }
            currentPosition = currentPosition.move(direction);
        }
        return true;
    }

    public Path getPathFrom(ChessPosition currentPosition) {
        List<ChessPosition> positions = new ArrayList<>();
        for (Direction direction : directions) {
            validatePosition(currentPosition, direction);
            final ChessPosition newPosition = currentPosition.move(direction);
            if (newPosition.isCastlePosition() && !currentPosition.canCastleMove(direction)) {
                continue;
            }
            positions.add(newPosition);
            currentPosition = newPosition;
        }
        return new Path(positions);
    }

    public Direction getFirstDirection() {
        return directions.getFirst();
    }

    private void validatePosition(ChessPosition currentPosition, Direction direction) {
        if (!currentPosition.canMove(direction)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }
}
