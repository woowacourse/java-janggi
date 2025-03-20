package janggiGame.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private static final Integer MIN_X_RANGE = 0;
    private static final Integer MAX_X_RANGE = 8;
    private static final Integer MIN_Y_RANGE = 0;
    private static final Integer MAX_Y_RANGE = 9;
    private static final List<Dot> dots = createDots();

    private static List<Dot> createDots() {
        List<Dot> dots = new ArrayList<>();
        for (int i = MAX_Y_RANGE; i >= MIN_Y_RANGE; i--) {
            for (int j = MIN_X_RANGE; j <= MAX_X_RANGE; j++) {
                dots.add(new Dot(j, i));
            }
        }
        return dots;
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

    public static Dot findBy(Integer x, Integer y) {
        validateDotRange(x, y);
        return dots.stream()
                .filter(d -> d.getX().equals(x))
                .filter(d -> d.getY().equals(y))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하는 좌표가 아닙니다."));
    }

    public static Dot getReverse(Dot dot) {
        return dots.stream()
                .filter(d -> d.getX() == MAX_X_RANGE - dot.getX())
                .filter(d -> d.getY() == MAX_Y_RANGE - dot.getY())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public static List<Dot> getDots() {
        return List.copyOf(dots);
    }
}
