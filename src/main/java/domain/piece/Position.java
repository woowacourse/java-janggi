package domain.piece;

public record Position(
        int column,
        int row
) {

    // TODO : 좌표 검증 로직 Board에서 챙기기
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
