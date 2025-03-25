package janggi.piece.direction;

import janggi.value.JanggiPosition;
import janggi.value.RelativePosition;
import java.util.Arrays;
import java.util.List;

public enum DiagonalDirection {

    LEFT_DOWN(List.of(new RelativePosition(-1,0)), new RelativePosition(-2,-1)),
    LEFT_UP(List.of(new RelativePosition(-1,0)), new RelativePosition(-2,1)),
    RIGHT_DOWN(List.of(new RelativePosition(1,0)), new RelativePosition(2,-1)),
    RIGHT_UP(List.of(new RelativePosition(1,0)), new RelativePosition(2,1)),
    UP_LEFT(List.of(new RelativePosition(0,1)), new RelativePosition(-1,2)),
    UP_RIGHT(List.of(new RelativePosition(0,1)), new RelativePosition(1,2)),
    DOWN_LEFT(List.of(new RelativePosition(0,-1)), new RelativePosition(-1,-2)),
    DOWN_RIGHT(List.of(new RelativePosition(0,-1)), new RelativePosition(1,-2)),
    LEFT_DOWN_DOWN(List.of(new RelativePosition(-1,0), new RelativePosition(-2,-1)), new RelativePosition(-3,-2)),
    LEFT_UP_UP(List.of(new RelativePosition(-1,0), new RelativePosition(-2,1)), new RelativePosition(-3,2)),
    RIGHT_DOWN_DOWN(List.of(new RelativePosition(1,0), new RelativePosition(2,-1)), new RelativePosition(3,-2)),
    RIGHT_UP_UP(List.of(new RelativePosition(1,0), new RelativePosition(2,1)), new RelativePosition(3,2)),
    UP_LEFT_LEFT(List.of(new RelativePosition(0,1), new RelativePosition(-1,2)), new RelativePosition(-2,3)),
    UP_RIGHT_RIGHT(List.of(new RelativePosition(0,1), new RelativePosition(1,2)), new RelativePosition(2,3)),
    DOWN_LEFT_LEFT(List.of(new RelativePosition(0,-1), new RelativePosition(-1,-2)), new RelativePosition(-2,-3)),
    DOWN_RIGHT_RIGHT(List.of(new RelativePosition(0,-1), new RelativePosition(1,-2)), new RelativePosition(2,-3)),
    ;

    private final List<RelativePosition> routeJanggiPositions;
    private final RelativePosition destinationJanggiPosition;

    DiagonalDirection(List<RelativePosition> routeJanggiPositions, RelativePosition destinationJanggiPosition) {
        this.routeJanggiPositions = routeJanggiPositions;
        this.destinationJanggiPosition = destinationJanggiPosition;
    }

    public static DiagonalDirection of(final JanggiPosition current, final JanggiPosition destination) {
        int dx = destination.x() - current.x();
        int dy = destination.y() - current.y();

        RelativePosition newRelativePosition = new RelativePosition(dx, dy);

        return Arrays.stream(DiagonalDirection.values())
                .filter(diagonalDirection -> diagonalDirection.destinationJanggiPosition.equals(newRelativePosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당하는 대각선 경로가 없습니다."));
    }

    public boolean isRoute(final JanggiPosition current, final JanggiPosition janggiPosition) {
        for (RelativePosition relativePosition : routeJanggiPositions) {
            JanggiPosition newJanggiPosition = new JanggiPosition(current.x() + relativePosition.x(),
                    current.y() + relativePosition.y());

            if (newJanggiPosition.equals(janggiPosition)) {
                return true;
            }
        }
        return false;
    }
}
