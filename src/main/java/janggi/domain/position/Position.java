package janggi.domain.position;

public record Position(
        Row row,
        Column column
) {

    public static Position OffsetFrom(int row, int col) {
        return null;
    }
}
