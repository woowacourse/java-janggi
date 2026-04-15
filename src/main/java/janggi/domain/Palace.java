package janggi.domain;

import java.util.List;
import java.util.Set;

public class Palace {

    public static final Palace CHO = new Palace(
            Set.of(
                    new Position(4, 8), new Position(5, 8), new Position(6, 8),
                    new Position(4, 9), new Position(5, 9), new Position(6, 9),
                    new Position(4, 10), new Position(5, 10), new Position(6, 10)
            ),
            List.of(
                    new Position(4, 10), new Position(4, 8),
                    new Position(6, 10), new Position(6, 8)
            ),
            new Position(5, 9)
    );

    public static final Palace HAN = new Palace(
            Set.of(
                    new Position(4, 1), new Position(5, 1), new Position(6, 1),
                    new Position(4, 2), new Position(5, 2), new Position(6, 2),
                    new Position(4, 3), new Position(5, 3), new Position(6, 3)
            ),
            List.of(
                    new Position(4, 1), new Position(4, 3),
                    new Position(6, 1), new Position(6, 3)
            ),
            new Position(5, 2)
    );

    private final Set<Position> zones;
    private final List<Position> vertices;
    private final Position center;

    private Palace(Set<Position> zones, List<Position> vertices, Position center) {
        this.zones = zones;
        this.vertices = vertices;
        this.center = center;
    }

    public Position calculateOppositeVertex(Position currentPosition) {
        if (!isVertex(currentPosition)) {
            throw new IllegalArgumentException("꼭짓점이 아닙니다.");
        }
        int currentColumn = currentPosition.getColumn();
        int currentRow = currentPosition.getRow();
        int centerColumn = center.getColumn();
        int centerRow = center.getRow();

        currentColumn = 2 * centerColumn - currentColumn;
        currentRow = 2 * centerRow - currentRow;

        return new Position(currentColumn, currentRow);
    }

    public static boolean isOutOfPalace(Position position) {
        return !CHO.contains(position) && !HAN.contains(position);
    }

    public static Palace getPalace(Position position) {
        if (CHO.contains(position)) {
            return CHO;
        }
        if (HAN.contains(position)) {
            return HAN;
        }
        return null;
    }

    public boolean contains(Position position) {
        return zones.contains(position);
    }

    public boolean isVertex(Position position) {
        return vertices.contains(position);
    }

    public boolean isCenter(Position position) {
        return center.equals(position);
    }

    public boolean isVertexOrCenter(Position position) {
        return isVertex(position) || isCenter(position);
    }

    public Position getCenter() {
        return center;
    }

}
