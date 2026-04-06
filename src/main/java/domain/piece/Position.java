package domain.piece;

public record Position(
        int row,
        int column
) {

    public static Position of(final int row, final int column) {
        return new Position(row, column);
    }

    public Position move(final Delta delta) {
        return Position.of(row + delta.row(), column + delta.column());
    }
}
