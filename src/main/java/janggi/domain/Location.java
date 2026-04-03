package janggi.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Location {
    private static final int COORDINATE_COUNT = 2;
    private static final int CACHE_RANGE = 10;

    private final int row;
    private final int col;

    private static final Map<String, Location> CACHE = new HashMap<>();

    static {
        for (int row = 0; row < CACHE_RANGE; row++) {
            for (int col = 0; col < CACHE_RANGE; col++) {
                CACHE.put(createKey(row, col), new Location(row, col));
            }
        }
    }

    private Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static Location of(int row, int col) {
        return CACHE.computeIfAbsent(createKey(row, col), key -> new Location(row, col));
    }

    public static Location from(List<Integer> position) {
        validateCount(position);
        return Location.of(position.get(0), position.get(1));
    }

    private static void validateCount(List<Integer> position) {
        if (position.size() != COORDINATE_COUNT) {
            throw new IllegalArgumentException("좌표의 개수는 " + COORDINATE_COUNT + "개 입니다.");
        }
    }

    public Location add(int dy, int dx) {
        return Location.of(row + dy, col + dx);
    }

    public int calculateHorizontalDiff(Location to) {
        return to.col - this.col;
    }

    public int calculateVerticalDiff(Location to) {
        return to.row - this.row;
    }

    private static String createKey(int row, int col) {
        return row + "," + col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return row == location.row && col == location.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }
}
