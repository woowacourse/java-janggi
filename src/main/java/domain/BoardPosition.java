package domain;

public record BoardPosition(
        int x,
        int y
) {

    public BoardPosition {
        validateRange(x, y);
    }

    private void validateRange(
            final int x,
            final int y
    ) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            throw new IllegalArgumentException("장기판의 범위를 벗어났습니다.");
        }
    }

    public BoardPosition calculatePosition(final Offset offset) {
        return new BoardPosition(this.x + offset.x(), this.y + offset.y());
    }

    public Offset calculateOffset(final BoardPosition before) {
        return new Offset(this.x - before.x, this.y - before.y);
    }
}
