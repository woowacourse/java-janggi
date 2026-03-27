package domain.position;

public record Coordinate(
        int row,
        int column
) {
    public static Coordinate rotate180from(int row, int column) {
        return new Coordinate(11 - row, 10 - column);
    }
}
