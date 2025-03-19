public class Dot {
    private static final Integer MIN_X_RANGE = 0;
    private static final Integer MAX_X_RANGE = 8;
    private static final Integer MIN_Y_RANGE = 0;
    private static final Integer MAX_Y_RANGE = 9;

    private final Integer x;
    private final Integer y;

    public Dot(Integer x, Integer y) {
        validateDotRange(x, y);
        this.x = x;
        this.y = y;
    }

    private void validateDotRange(Integer x, Integer y) {
        validateXRange(x);
        validateYRange(y);
    }

    private void validateXRange(Integer x) {
        if(x < MIN_X_RANGE || x > MAX_X_RANGE) {
            throw new IllegalArgumentException("[ERROR] x 좌표의 범위가 벗어났습니다.");
        }
    }

    private void validateYRange(Integer y) {
        if(y < MIN_Y_RANGE || y > MAX_Y_RANGE) {
            throw new IllegalArgumentException("[ERROR] y 좌표의 범위가 벗어났습니다.");
        }
    }



}
