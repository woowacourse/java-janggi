package domain.board;

public record Offset(
        int x,
        int y
) {
    private static final int MAXIMUM_ROW = 9;
    private static final int MAXIMUM_COLUMN = 8;

    public static final Offset UP = new Offset(0, 1);
    public static final Offset DOWN = new Offset(0, -1);
    public static final Offset LEFT = new Offset(-1, 0);
    public static final Offset RIGHT = new Offset(1, 0);
    public static final Offset RIGHT_UP = new Offset(1, 1);
    public static final Offset RIGHT_DOWN = new Offset(1, -1);
    public static final Offset LEFT_UP = new Offset(-1, 1);
    public static final Offset LEFT_DOWN = new Offset(-1, -1);

    public Offset {
        validateRange(x, y);
    }

    private void validateRange(
            final int x,
            final int y
    ) {
        if (Math.abs(x) > MAXIMUM_COLUMN || Math.abs(y) > MAXIMUM_ROW) {
            throw new IllegalArgumentException("오프셋의 범위를 벗어났습니다.");
        }
    }

    public boolean hasNoMove() {
        return x == 0 && y == 0;
    }

    public boolean hasOneStraightMove() {
        return (x == 0 && Math.abs(y) == 1) || (Math.abs(x) == 1 && y == 0);
    }

    public boolean hasOneDiagonalMove() {
        return (Math.abs(y) == 1) && (Math.abs(x) == 1);
    }

    public boolean isStraightMove() {
        return x == 0 || y == 0;
    }

    public boolean isDiagonalMove() {
        return Math.abs(x) == Math.abs(y);
    }

    public boolean isUpDirectionMove() {
        return y > 0;
    }

    public boolean isDownDirectionMove() {
        return y < 0;
    }

    public Offset getUnitDirectionOffset() {
        return new Offset(Integer.compare(this.x, 0), Integer.compare(this.y, 0));
    }

    public Offset plus(final Offset offset) {
        return new Offset(this.x + offset.x, this.y + offset.y);
    }

    public static Offset origin() {
        return new Offset(0, 0);
    }
}
