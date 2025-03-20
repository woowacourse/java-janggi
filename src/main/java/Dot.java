import java.util.ArrayList;
import java.util.List;

public class Dot {
    private static final Integer MIN_X_RANGE = 0;
    private static final Integer MAX_X_RANGE = 8;
    private static final Integer MIN_Y_RANGE = 0;
    private static final Integer MAX_Y_RANGE = 9;
    public static final List<Dot> CACHE = createDotCache();
    private final Integer x;
    private final Integer y;

    public Dot(Integer x, Integer y) {
        validateDotRange(x, y);
        this.x = x;
        this.y = y;
    }

    private static List<Dot> createDotCache() {
        List<Dot> dots = new ArrayList<>();
        for (int i = MAX_Y_RANGE; i >= MIN_Y_RANGE; i--) {
            for (int j = MIN_X_RANGE; j <= MAX_X_RANGE; j++) {
                dots.add(new Dot(j, i));
            }
        }
        return dots;
    }

    public static Dot of(int x, int y) {
        return CACHE.stream()
                .filter(d -> d.x == x && d.y == y)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 좌표입니다."));
    }

    private boolean has(int x, int y) {
        return this.x == x && this.y == y;
    }

    public boolean hasSameX(Dot other) {
        return this.x.equals(other.x);
    }

    public boolean hasSameY(Dot other) {
        return this.y.equals(other.y);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public int getDx(Dot other) {
        return other.x - this.x;
    }

    public int getDy(Dot other) {
        return other.y - this.y;
    }

    private void validateDotRange(Integer x, Integer y) {
        validateXRange(x);
        validateYRange(y);
    }

    private void validateXRange(Integer x) {
        if (x < MIN_X_RANGE || x > MAX_X_RANGE) {
            throw new IllegalArgumentException("[ERROR] x 좌표의 범위가 벗어났습니다.");
        }
    }

    private void validateYRange(Integer y) {
        if (y < MIN_Y_RANGE || y > MAX_Y_RANGE) {
            throw new IllegalArgumentException("[ERROR] y 좌표의 범위가 벗어났습니다.");
        }
    }

    public Dot reverse() {
        return Dot.of(MAX_X_RANGE - this.x, MAX_Y_RANGE - this.y);
    }

}
