package janggi.domain.piece.direction;

import janggi.domain.value.JanggiPosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FourDirection {
    LEFT(new DirectionVector(-1, 0)),
    RIGHT(new DirectionVector(1, 0)),
    UP(new DirectionVector(0, 1)),
    DOWN(new DirectionVector(0, -1)),
    ;

    private final DirectionVector directionVector;

    FourDirection(final DirectionVector directionVector) {
        this.directionVector = directionVector;
    }

    public static List<JanggiPosition> from(JanggiPosition destination, JanggiPosition position) {
        int dx = Integer.compare(destination.x(), position.x());
        int dy = Integer.compare(destination.y(), position.y());
        FourDirection fourDirection = findDirection(dx, dy);

        return generatePositions(position, destination, fourDirection.directionVector);
    }

    private static FourDirection findDirection(int dx, int dy) {
        return Arrays.stream(values())
                .filter(direction -> direction.directionVector.x() == dx && direction.directionVector.y() == dy)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동 가능한 방향이 없습니다."));
    }

    private static List<JanggiPosition> generatePositions(JanggiPosition start, JanggiPosition end, DirectionVector step) {
        List<JanggiPosition> positions = new ArrayList<>();
        int x = start.x();
        int y = start.y();

        while (x != end.x() || y != end.y()) {
            x += step.x();
            y += step.y();
            positions.add(new JanggiPosition(x, y));
        }
        return positions;
    }
}
