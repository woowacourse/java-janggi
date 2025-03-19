import java.util.ArrayList;
import java.util.List;

public class Dot {
    private static final Integer MIN_X_RANGE = 0;
    private static final Integer MAX_X_RANGE = 8;
    private static final Integer MIN_Y_RANGE = 0;
    private static final Integer MAX_Y_RANGE = 9;

    private final Integer x;
    private final Integer y;

    private static final List<Dot> CACHE = createDotCache();

    private static List<Dot> createDotCache() {
        List<Dot> dots = new ArrayList<>();
        for(int i = MIN_X_RANGE; i <= MAX_X_RANGE; i++) {
            for (int j = MIN_Y_RANGE; j <= MAX_Y_RANGE; j++) {
                dots.add(new Dot(i, j));
            }
        }
        return dots;
    }

    public static Dot of(int x, int y) {
        return CACHE.stream()
                .filter(d -> d.x == x && d.y == y)
                .findFirst()
                .orElse(new Dot(x, y));
    }

    private boolean has(int x, int y) {
        return this.x == x && this.y == y;
    }

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
