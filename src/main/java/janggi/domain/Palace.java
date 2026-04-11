package janggi.domain;

import java.util.List;
import java.util.Set;

public class Palace {

    private static final Set<Position> CHO_PALACE_ZONES =
            Set.of(
                    new Position(4, 8),
                    new Position(5, 8),
                    new Position(6, 8),
                    new Position(4, 9),
                    new Position(5, 9),
                    new Position(6, 9),
                    new Position(4, 10),
                    new Position(5, 10),
                    new Position(6, 10)
            );
    private static final Set<Position> HAN_PALACE_ZONES =
            Set.of(
                    new Position(4, 1),
                    new Position(5, 1),
                    new Position(6, 1),
                    new Position(4, 2),
                    new Position(5, 2),
                    new Position(6, 2),
                    new Position(4, 3),
                    new Position(5, 3),
                    new Position(6, 3)
            );
    private static final List<Position> CHO_PALACE_VERTEX = List.of(
            new Position(4, 10),
            new Position(4, 8),
            new Position(6, 10),
            new Position(6, 8)
    );
    private static final List<Position> HAN_PALACE_VERTEX = List.of(
            new Position(4, 1),
            new Position(4, 3),
            new Position(6, 1),
            new Position(6, 3)
    );
    public static final Position CHO_PALACE_CENTER = new Position(5, 9);
    public static final Position HAN_PALACE_CENTER = new Position(5, 2);

    public static boolean isOutOfPalace(Position position) {
        return !isInChoPalace(position) && !isInHanPalace(position);
    }

    private static boolean isInChoPalace(Position position) {
        return CHO_PALACE_ZONES.contains(position);
    }

    private static boolean isInHanPalace(Position position) {
        return HAN_PALACE_ZONES.contains(position);
    }

    public static boolean isInChoPalaceVertex(Position position) {
        return CHO_PALACE_VERTEX.contains(position);
    }

    public static boolean isInHanPalaceVertex(Position position) {
        return HAN_PALACE_VERTEX.contains(position);
    }

    public static Position calculateOppositePalaceDiagonalPosition(Position currentPosition) {
        if (isInChoPalaceVertex(currentPosition)) {
            return calculatePointReflection(currentPosition, CHO_PALACE_CENTER);
        }
        return calculatePointReflection(currentPosition, HAN_PALACE_CENTER);
    }

    private static Position calculatePointReflection(Position currentPosition, Position center) {
        int currentColumn = currentPosition.getColumn();
        int currentRow = currentPosition.getRow();
        int centerColumn = center.getColumn();
        int centerRow = center.getRow();

        currentColumn = 2 * centerColumn - currentColumn;
        currentRow = 2 * centerRow - currentRow;

        return new Position(currentColumn, currentRow);
    }
}
