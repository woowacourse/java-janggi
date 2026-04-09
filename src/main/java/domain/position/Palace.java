package domain.position;

import java.util.Arrays;
import java.util.Optional;

public enum Palace {
    TOP(new Position(1, 4)),
    BOTTOM(new Position(8, 4));

    private static final int RANGE = 1;
    private static final int MIN_COL = 3;
    private static final int MAX_COL = 5;

    private final Position center;

    Palace(Position center) {
        this.center = center;
    }

    public static Optional<Palace> findBy(Position position) {
        return Arrays.stream(values())
                .filter(palace -> palace.contains(position))
                .findFirst();
    }

    public boolean contains(Position position) {
        return isWithinRowRange(position) && isWithinColRange(position);
    }

    public boolean isDiagonalReachable(Position source, Position destination) {
        if (source.equals(destination) || !contains(source) || !contains(destination)) {
            return false;
        }

        if (!isOnDiagonalLine(source) || !isOnDiagonalLine(destination)) {
            return false;
        }

        return source.isDiagonalWith(destination);
    }

    private boolean isWithinRowRange(Position position) {
        return position.rowDiff(center) <= RANGE;
    }

    private boolean isWithinColRange(Position position) {
        return position.column() >= MIN_COL && position.column() <= MAX_COL;
    }

    private boolean isOnDiagonalLine(Position position) {
        return position.rowDiff(center) == position.colDiff(center);
    }
}