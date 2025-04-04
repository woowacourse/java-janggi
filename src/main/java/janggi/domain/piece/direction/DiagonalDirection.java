package janggi.domain.piece.direction;

import janggi.domain.value.JanggiPosition;
import java.util.Arrays;
import java.util.List;

public enum DiagonalDirection {

    LEFT_DOWN(List.of(new DirectionVector(-1,0)), new DirectionVector(-2,-1)),
    LEFT_UP(List.of(new DirectionVector(-1,0)), new DirectionVector(-2,1)),
    RIGHT_DOWN(List.of(new DirectionVector(1,0)), new DirectionVector(2,-1)),
    RIGHT_UP(List.of(new DirectionVector(1,0)), new DirectionVector(2,1)),
    UP_LEFT(List.of(new DirectionVector(0,1)), new DirectionVector(-1,2)),
    UP_RIGHT(List.of(new DirectionVector(0,1)), new DirectionVector(1,2)),
    DOWN_LEFT(List.of(new DirectionVector(0,-1)), new DirectionVector(-1,-2)),
    DOWN_RIGHT(List.of(new DirectionVector(0,-1)), new DirectionVector(1,-2)),
    LEFT_DOWN_DOWN(List.of(new DirectionVector(-1,0), new DirectionVector(-2,-1)), new DirectionVector(-3,-2)),
    LEFT_UP_UP(List.of(new DirectionVector(-1,0), new DirectionVector(-2,1)), new DirectionVector(-3,2)),
    RIGHT_DOWN_DOWN(List.of(new DirectionVector(1,0), new DirectionVector(2,-1)), new DirectionVector(3,-2)),
    RIGHT_UP_UP(List.of(new DirectionVector(1,0), new DirectionVector(2,1)), new DirectionVector(3,2)),
    UP_LEFT_LEFT(List.of(new DirectionVector(0,1), new DirectionVector(-1,2)), new DirectionVector(-2,3)),
    UP_RIGHT_RIGHT(List.of(new DirectionVector(0,1), new DirectionVector(1,2)), new DirectionVector(2,3)),
    DOWN_LEFT_LEFT(List.of(new DirectionVector(0,-1), new DirectionVector(-1,-2)), new DirectionVector(-2,-3)),
    DOWN_RIGHT_RIGHT(List.of(new DirectionVector(0,-1), new DirectionVector(1,-2)), new DirectionVector(2,-3)),
    ;

    private final List<DirectionVector> routeJanggiPositions;
    private final DirectionVector destinationJanggiPosition;

    DiagonalDirection(List<DirectionVector> routeJanggiPositions, DirectionVector destinationJanggiPosition) {
        this.routeJanggiPositions = routeJanggiPositions;
        this.destinationJanggiPosition = destinationJanggiPosition;
    }

    public static DiagonalDirection of(final JanggiPosition current, final JanggiPosition destination) {
        int dx = destination.x() - current.x();
        int dy = destination.y() - current.y();

        DirectionVector newDirectionVector = new DirectionVector(dx, dy);

        return Arrays.stream(DiagonalDirection.values())
                .filter(diagonalDirection -> diagonalDirection.destinationJanggiPosition.equals(newDirectionVector))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당하는 대각선 경로가 없습니다."));
    }

    public boolean isRoute(final JanggiPosition current, final JanggiPosition janggiPosition) {
        for (DirectionVector directionVector : routeJanggiPositions) {
            JanggiPosition newJanggiPosition = new JanggiPosition(current.x() + directionVector.x(),
                    current.y() + directionVector.y());

            if (newJanggiPosition.equals(janggiPosition)) {
                return true;
            }
        }
        return false;
    }
}
