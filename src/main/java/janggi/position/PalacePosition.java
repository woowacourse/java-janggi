package janggi.position;

import java.util.Arrays;
import java.util.List;

public enum PalacePosition {
    EDGE_POSITION(List.of(
            new Position(8,4),
            new Position(8,6),
            new Position(10,4),
            new Position(10,6),
            new Position(3,4),
            new Position(3,6))),
    CENTER_POSITION(List.of(
            new Position(9,5),
            new Position(2,5)));

    private final List<Position> values;

    PalacePosition(List<Position> values) {
        this.values = values;
    }

    public static boolean isContains(Position position) {
        return Arrays.stream(PalacePosition.values())
                .anyMatch(value -> value.contains(position));
    }

    public boolean contains(Position position) {
        return values.stream()
                .anyMatch(value -> value.equals(position));
    }
}
