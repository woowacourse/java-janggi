package domain;

public record Offset(
        int x,
        int y
) {

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
        if (Math.abs(x) > 8 || Math.abs(y) > 9) {
            throw new IllegalArgumentException("오프셋의 범위를 벗어났습니다.");
        }
    }
}
