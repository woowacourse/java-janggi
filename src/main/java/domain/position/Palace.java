package domain.position;

import java.util.Arrays;
import java.util.Optional;

public enum Palace {
    TOP(1),
    BOTTOM(8);

    private static final int PALACE_HALF_HEIGHT = 1;
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;
    private static final int PALACE_CENTER_COLUMN = 4;

    private final int centerRow;

    Palace(int centerRow) {
        this.centerRow = centerRow;
    }

    public static Optional<Palace> findBy(Position position) {
        return Arrays.stream(values())
                .filter(palace -> palace.contains(position))
                .findFirst();
    }

    public boolean contains(Position position) {
        return isInPalaceRow(position) && isInPalaceColumn(position);
    }

    public boolean isDiagonalReachable(Position source, Position destination) {
        if (source.equals(destination) || !contains(source) || !contains(destination)) {
            return false;
        }

        if (isNotOnXLine(source) || isNotOnXLine(destination)) {
            return false;
        }

        int rowDiff = Math.abs(source.row() - destination.row());
        int colDiff = Math.abs(source.column() - destination.column());
        return rowDiff == colDiff;
    }

    private boolean isInPalaceRow(Position position) {
        return Math.abs(position.row() - centerRow) <= PALACE_HALF_HEIGHT;
    }

    private boolean isInPalaceColumn(Position position) {
        return position.column() >= PALACE_MIN_COLUMN && position.column() <= PALACE_MAX_COLUMN;
    }

    private boolean isNotOnXLine(Position position) {
        return Math.abs(position.row() - centerRow) != Math.abs(position.column() - PALACE_CENTER_COLUMN);
    }
}