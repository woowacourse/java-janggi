package domain.board;

public record BoardPosition(
    int x,
    int y
) {

    public static final BoardPosition GREEN_PALACE_SOUTH_WEST = new BoardPosition(3, 0);
    public static final BoardPosition GREEN_PALACE_SOUTH_CENTER = new BoardPosition(4, 0);
    public static final BoardPosition GREEN_PALACE_SOUTH_EAST = new BoardPosition(5, 0);
    public static final BoardPosition GREEN_PALACE_MIDDLE_WEST = new BoardPosition(3, 1);
    public static final BoardPosition GREEN_PALACE_MIDDLE_CENTER = new BoardPosition(4, 1);
    public static final BoardPosition GREEN_PALACE_MIDDLE_EAST = new BoardPosition(5, 1);
    public static final BoardPosition GREEN_PALACE_NORTH_WEST = new BoardPosition(3, 2);
    public static final BoardPosition GREEN_PALACE_NORTH_CENTER = new BoardPosition(4, 2);
    public static final BoardPosition GREEN_PALACE_NORTH_EAST = new BoardPosition(5, 2);
    public static final BoardPosition RED_PALACE_SOUTH_WEST = new BoardPosition(3, 7);
    public static final BoardPosition RED_PALACE_SOUTH_CENTER = new BoardPosition(4, 7);
    public static final BoardPosition RED_PALACE_SOUTH_EAST = new BoardPosition(5, 7);
    public static final BoardPosition RED_PALACE_MIDDLE_WEST = new BoardPosition(3, 8);
    public static final BoardPosition RED_PALACE_MIDDLE_CENTER = new BoardPosition(4, 8);
    public static final BoardPosition RED_PALACE_MIDDLE_EAST = new BoardPosition(5, 8);
    public static final BoardPosition RED_PALACE_NORTH_WEST = new BoardPosition(3, 9);
    public static final BoardPosition RED_PALACE_NORTH_CENTER = new BoardPosition(4, 9);
    public static final BoardPosition RED_PALACE_NORTH_EAST = new BoardPosition(5, 9);

    public static final int COORDINATE_PARTS_COUNT = 2;
    public static final int MIN_X = 0;
    public static final int MAX_X = 8;
    public static final int MIN_Y = 0;
    public static final int MAX_Y = 9;

    public BoardPosition {  // TODO. 캐싱 적용
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
