package domain.piece;

public record Position(
        int column,
        int row
) {
    public static Position of(final int column, final int row) {
        return new Position(column, row);
    }

    public Position move(final Position offset) {
        return Position.of(column + offset.column, row + offset.row);
    }

    public Position move(final Delta delta) {
        return Position.of(column + delta.column(), row + delta.row());
    }
}
