package janggi.domain.piece;

import java.util.Arrays;
import java.util.List;

public enum Palace {
    HAN_PALACE(
            List.of(new Point(1, 4), new Point(1, 5), new Point(1, 6),
                    new Point(2, 4), new Point(2, 5), new Point(2, 6),
                    new Point(3, 4), new Point(3, 5), new Point(3, 6)),
            List.of(new Point(1, 4), new Point(1, 6),
                    new Point(2, 5),
                    new Point(3, 4), new Point(3, 6))
    ),
    CHU_PALACE(
            List.of(new Point(8, 4), new Point(8, 5), new Point(8, 6),
                    new Point(9, 4), new Point(9, 5), new Point(9, 6),
                    new Point(10, 4), new Point(10, 5), new Point(10, 6)),
            List.of(new Point(8, 4), new Point(8, 6),
                    new Point(9, 5),
                    new Point(10, 4), new Point(10, 6))
    );

    private final List<Point> palacePoints;
    private final List<Point> additionalMovementPoints;

    Palace(List<Point> palacePoints, List<Point> additionalMovementPoints) {
        this.palacePoints = palacePoints;
        this.additionalMovementPoints = additionalMovementPoints;
    }

    public static boolean canMoveInPalace(Point from, Point to, Direction direction) {
        if (!isSamePalace(from, to)) {
            return false;
        }

        if (direction.isDiagonal()) {
            return canMoveDiagonal(from, to);
        }

        return true;
    }

    public static boolean isInPalace(Point point) {
        return Arrays.stream(values())
                .anyMatch(palace -> palace.palacePoints.contains(point));
    }

    private static boolean isSamePalace(Point from, Point to) {
        Palace fromPalace = findPalaceInPalacePoints(from);
        Palace toPalace = findPalaceInPalacePoints(to);

        if (fromPalace == null || toPalace == null) {
            return false;
        }

        return fromPalace == toPalace;
    }

    private static boolean canMoveDiagonal(Point from, Point to) {
        Palace fromPalace = findPalaceInAdditionalMovementPoints(from);
        Palace toPalace = findPalaceInAdditionalMovementPoints(to);
        if (fromPalace == null || toPalace == null) {
            return false;
        }

        return fromPalace == toPalace && Direction.isDiagonal(from, to);
    }

    private static Palace findPalaceInAdditionalMovementPoints(Point point) {
        return Arrays.stream(values())
                .filter(palace -> palace.additionalMovementPoints.contains(point))
                .findFirst()
                .orElse(null);
    }

    private static Palace findPalaceInPalacePoints(Point point) {
        return Arrays.stream(values())
                .filter(palace -> palace.palacePoints.contains(point))
                .findFirst()
                .orElse(null);
    }
}
