package domain.board;

import java.util.Set;

public record BoardPosition(
        int x,
        int y
) {

    private static final Set<BoardPosition> PALACE_POSITIONS = Set.of(
            new BoardPosition(3, 0), new BoardPosition(4, 0), new BoardPosition(5, 0),
            new BoardPosition(3, 1), new BoardPosition(4, 1), new BoardPosition(5, 1),
            new BoardPosition(3, 2), new BoardPosition(4, 2), new BoardPosition(5, 2),
            new BoardPosition(3, 7), new BoardPosition(4, 7), new BoardPosition(5, 7),
            new BoardPosition(3, 8), new BoardPosition(4, 8), new BoardPosition(5, 8),
            new BoardPosition(3, 9), new BoardPosition(4, 9), new BoardPosition(5, 9)
    );

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

    public boolean isPalaceArea() {
        return PALACE_POSITIONS.contains(this);
    }

    public BoardPosition plus(final Offset offset) {
        return new BoardPosition(this.x + offset.x(), this.y + offset.y());
    }

    public Offset calculateOffset(final BoardPosition before) {
        return new Offset(this.x - before.x, this.y - before.y);
    }
}
