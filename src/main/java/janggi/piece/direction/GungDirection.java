package janggi.piece.direction;

import janggi.value.JanggiPosition;
import janggi.value.RelativePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum GungDirection {
    RIGHT(List.of(), new RelativePosition(1,0)),
    RIGHT_RIGHT(List.of(new RelativePosition(1,0)), new RelativePosition(2,0)),
    LEFT(List.of(), new RelativePosition(-1,0)),
    LEFT_LEFT(List.of(new RelativePosition(-1,0)), new RelativePosition(-2,0)),
    UP(List.of(), new RelativePosition(0,1)),
    UP_UP(List.of(new RelativePosition(0,1)), new RelativePosition(0,2)),
    DOWN(List.of(), new RelativePosition(0,-1)),
    DOWN_DOWN(List.of(new RelativePosition(0,-1)), new RelativePosition(0,-2)),
    LEFT_TO_RIGHT_DOWN_DIAGONAL(List.of(), new RelativePosition(1,1)),
    RIGHT_TO_LEFT_DOWN_DIAGONAL(List.of(), new RelativePosition(-1,1)),
    LEFT_TO_RIGHT_UP_DIAGONAL(List.of(), new RelativePosition(1,-1)),
    RIGHT_TO_LEFT_UP_DIAGONAL(List.of(), new RelativePosition(-1,-1)),
    LEFT_TO_RIGHT_TWO_DOWN_DIAGONAL(List.of(new RelativePosition(1,1)), new RelativePosition(2,2)),
    RIGHT_TO_LEFT_TWO_DOWN_DIAGONAL(List.of(new RelativePosition(-1,1)), new RelativePosition(-2,2)),
    LEFT_TO_RIGHT_TWO_UP_DIAGONAL(List.of(new RelativePosition(1,-1)), new RelativePosition(2,-2)),
    RIGHT_TO_LEFT_TWO_UP_DIAGONAL(List.of(new RelativePosition(-1,-1)), new RelativePosition(-2,-2)),
    ;
    private final List<RelativePosition> routes;
    private final RelativePosition destinationPosition;

    GungDirection(final List<RelativePosition> routes, final RelativePosition destinationPosition) {
        this.routes = routes;
        this.destinationPosition = destinationPosition;
    }

    public static List<JanggiPosition> of(final JanggiPosition currentPosition, final JanggiPosition destination) {
        int dx = destination.x() - currentPosition.x();
        int dy = destination.y() - currentPosition.y();

        GungDirection gungDirection = findDirection(dx, dy);

        return generatePositions(currentPosition, destination, gungDirection.routes);
    }

    private static GungDirection findDirection(int dx, int dy) {
        return Arrays.stream(GungDirection.values())
                .filter(gungDirection -> gungDirection.destinationPosition.equals(new RelativePosition(dx,dy)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 궁성 내에서 이동 가능한 경로가 없습니다."));
    }

    private static List<JanggiPosition> generatePositions(JanggiPosition start, JanggiPosition end, List<RelativePosition> routes) {
        List<JanggiPosition> positions = new ArrayList<>();
        if (routes.isEmpty()) {
            positions.add(end);
            return positions;
        }

        for (RelativePosition relativePosition : routes) {
            positions.add(new JanggiPosition(relativePosition.x() + start.x(), relativePosition.y() + start.y()));
        }
        positions.add(end);
        return positions;
    }
}
