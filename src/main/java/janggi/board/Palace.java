package janggi.board;

import java.util.List;

public class Palace {
    public static final List<Position> bottomPalaceCorners = List.of(
            new Position(3, 0),
            new Position(5, 0),
            new Position(3, 2),
            new Position(5, 2)
    );
    public static final List<Position> upperPalaceCorners = List.of(
            new Position(3, 7),
            new Position(5, 7),
            new Position(3, 9),
            new Position(5, 9)
    );
    public static final Position bottomPalaceCenter = new Position(4, 1);
    public static final Position upperPalaceCenter = new Position(4, 8);

    public boolean isInnerBottomPalace(Position position) {
        return (position.isIncludedColumnRange(3, 5)) && (position.isIncludedRowRange(0, 2));
    }

    public boolean isInnerUpperPalace(Position position) {
        return (position.isIncludedColumnRange(3, 5)) && (position.isIncludedRowRange(7, 9));
    }

    public boolean isBottomPalaceCorner(Position other) {
        return bottomPalaceCorners.stream()
                .anyMatch(position -> position.equals(other));
    }

    public boolean isUpperPalaceCorner(Position other) {
        return upperPalaceCorners.stream()
                .anyMatch(position -> position.equals(other));
    }

    public Position getBottomPalaceCenter() {
        return bottomPalaceCenter;
    }

    public Position getUpperPalaceCenter() {
        return upperPalaceCenter;
    }
}
