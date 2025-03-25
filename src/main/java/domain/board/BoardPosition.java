package domain.board;

public record BoardPosition(
        int x,
        int y
) {

    private static final int MINIMUM_COLUMN = 0;
    private static final int MAXIMUM_COLUMN = 8;
    private static final int MINIMUM_ROW = 0;
    private static final int MAXIMUM_ROW = 9;

    public BoardPosition {
        validateRange(x, y);
    }

    private void validateRange(
            final int x,
            final int y
    ) {
        if (x < MINIMUM_COLUMN || x > MAXIMUM_COLUMN || y < MINIMUM_ROW || y > MAXIMUM_ROW) {
            throw new IllegalArgumentException("장기판의 범위를 벗어났습니다.");
        }
    }

    public BoardPosition plus(final Offset offset) {
        return new BoardPosition(this.x + offset.x(), this.y + offset.y());
    }

    public Offset calculateOffset(final BoardPosition before) {
        return new Offset(this.x - before.x, this.y - before.y);
    }
}
