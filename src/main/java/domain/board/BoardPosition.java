package domain.board;

public record BoardPosition(
    int x,
    int y
) {

    public static final int COORDINATE_PARTS_COUNT = 2;
    public static final int MIN_X = 0;
    public static final int MAX_X = 8;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 9;

    public BoardPosition {
        validateRange(x, y);
    }

    private void validateRange(
        final int x,
        final int y
    ) {
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new IllegalArgumentException("장기판의 범위를 벗어났습니다.");
        }
    }

    public BoardPosition calculatePosition(final Offset offset) {
        return new BoardPosition(this.x + offset.x(), this.y + offset.y());
    }

    public Offset calculateOffset(final BoardPosition source) {
        return new Offset(this.x - source.x, this.y - source.y);
    }
}
