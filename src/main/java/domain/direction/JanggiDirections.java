package domain.direction;

import domain.path.Path;
import domain.position.JanggiPosition;

import java.util.ArrayList;
import java.util.List;

public class JanggiDirections {
    private final List<Direction> directions;

    public JanggiDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean canApplyFrom(final JanggiPosition startPosition) {
        JanggiPosition currentPosition = startPosition;
        for (Direction direction : directions) {
            if (!currentPosition.canMove(direction)) {
                return false;
            }
            currentPosition = currentPosition.move(direction);
        }
        return true;
    }

    public Path getPathFrom(JanggiPosition currentPosition) {
        List<JanggiPosition> positions = new ArrayList<>();
        for (Direction direction : directions) {
            validatePosition(currentPosition, direction);
            currentPosition = currentPosition.move(direction);
            positions.add(currentPosition);
        }
        return new Path(positions);
    }

    private void validatePosition(JanggiPosition currentPosition, Direction direction) {
        if (!currentPosition.canMove(direction)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }
}
