package janggiGame.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dot {
    private static final Integer MIN_X_RANGE = 0;
    private static final Integer MAX_X_RANGE = 8;
    private static final Integer MIN_Y_RANGE = 0;
    private static final Integer MAX_Y_RANGE = 9;
    private static final List<Dot> dots = createDots();

    private final Integer x;
    private final Integer y;

    public Dot(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    private static List<Dot> createDots() {
        List<Dot> dots = new ArrayList<>();
        for (int i = MAX_Y_RANGE; i >= MIN_Y_RANGE; i--) {
            for (int j = MIN_X_RANGE; j <= MAX_X_RANGE; j++) {
                dots.add(new Dot(j, i));
            }
        }
        return dots;
    }

    public static List<Dot> getDots() {
        return List.copyOf(dots);
    }

    public static Dot findBy(int x, int y) {
        validateDotRange(x, y);
        return dots.stream()
                .filter(d -> d.x == x && d.y == y)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하는 좌표가 아닙니다."));
    }

    private static void validateDotRange(Integer x, Integer y) {
        validateXRange(x);
        validateYRange(y);
    }

    private static void validateXRange(Integer x) {
        if (x < MIN_X_RANGE || x > MAX_X_RANGE) {
            throw new IllegalArgumentException("[ERROR] x 좌표의 범위가 벗어났습니다.");
        }
    }

    private static void validateYRange(Integer y) {
        if (y < MIN_Y_RANGE || y > MAX_Y_RANGE) {
            throw new IllegalArgumentException("[ERROR] y 좌표의 범위가 벗어났습니다.");
        }
    }

    public Dot getReverse() {
        return dots.stream()
                .filter(d -> d.x == MAX_X_RANGE - this.x)
                .filter(d -> d.y == MAX_Y_RANGE - this.y)
                .findFirst()
                .orElseThrow(RuntimeException::new);
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

    public Dot up() {
        return findBy(x, y + 1);
    }

    public Dot down() {
        return findBy(x, y - 1);
    }

    public Dot right() {
        return findBy(x + 1, y);
    }

    public Dot left() {
        return findBy(x - 1, y);
    }

    public Dot upRight() {
        return findBy(x + 1, y + 1);
    }

    public Dot upLeft() {
        return findBy(x - 1, y + 1);
    }

    public Dot downRight() {
        return findBy(x + 1, y - 1);
    }

    public Dot downLeft() {
        return findBy(x - 1, y - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dot dot = (Dot) o;
        return Objects.equals(x, dot.x) && Objects.equals(y, dot.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
