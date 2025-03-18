public class LocationX {
    private static final Integer MIN_VALUE = 0;
    private static final Integer MAX_VALUE = 8;

    private final Integer x;

    public LocationX(int x) {
        validateRange(x);
        this.x = x;
    }

    private void validateRange(final int x) {
        if (x < MIN_VALUE || x > MAX_VALUE) {
            throw new IllegalArgumentException("[ERROR] x의 범위가 잘못되었습니다.");
        }
    }
}
