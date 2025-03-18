public class LocationY {
    private static final Integer MIN_VALUE = 0;
    private static final Integer MAX_VALUE = 9;

    private final Integer y;

    public LocationY(int y) {
        validateRange(y);
        this.y = y;
    }

    private void validateRange(final int y) {
        if (y < MIN_VALUE || y > MAX_VALUE) {
            throw new IllegalArgumentException("[ERROR] y의 범위가 잘못되었습니다.");
        }
    }
}
