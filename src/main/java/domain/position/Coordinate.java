package domain.position;

public record Coordinate(
        int row,
        int column
) {
    private static final int FLIPPED_ROW_BASE = 11;
    private static final int FLIPPED_COLUMN_BASE = 10;

    public static Coordinate rotate180from(int row, int column) {
        return new Coordinate(FLIPPED_ROW_BASE - row, FLIPPED_COLUMN_BASE - column);
    }
}
