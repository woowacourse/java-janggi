package janggi.domain;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final List<List<Point>> CACHE;

    private static final int PALACE_MIN_X = 3;
    private static final int PALACE_MAX_X = 5;

    private static final int CHO_PALACE_MIN_Y = 0;
    private static final int CHO_PALACE_MAX_Y = 2;

    private static final int HAN_PALACE_MIN_Y = 7;
    private static final int HAN_PALACE_MAX_Y = 9;

    private final int x;
    private final int y;

    static {
        List<List<Point>> points = new ArrayList<>();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            List<Point> pointsAtY = new ArrayList<>();
            addPointsInX(pointsAtY, i);
            points.add(pointsAtY);
        }
        CACHE = Collections.unmodifiableList(points);
    }

    private Point(int x, int y) {
        validateRange(x, y);
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        validateRange(x, y);
        return CACHE.get(y).get(x);
    }

    public static List<Point> getPointsAtY(int y) {
        return CACHE.get(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPathX(Point from) {
        return this.x - from.x;
    }

    public int getPathY(Point from) {
        return this.y - from.y;
    }

    public boolean isInSamePalace(Point to) {
        return (isInChoPalace() && to.isInChoPalace())
                || (isInHanPalace() && to.isInHanPalace());
    }

    public boolean isPalaceDiagonalMove(Point to) {
        if (!isInSamePalace(to)) {
            return false;
        }
        return isChoPalaceDiagonalPair(to) || isHanPalaceDiagonalPair(to);
    }

    private boolean isChoPalaceDiagonalPair(Point to) {
        return isSamePointPair(to, Point.of(3, 0), Point.of(4, 1))
                || isSamePointPair(to, Point.of(4, 1), Point.of(5, 2))
                || isSamePointPair(to, Point.of(5, 0), Point.of(4, 1))
                || isSamePointPair(to, Point.of(4, 1), Point.of(3, 2))
                || isSamePointPair(to, Point.of(3, 0), Point.of(5, 2))
                || isSamePointPair(to, Point.of(5, 0), Point.of(3, 2));
    }

    private boolean isHanPalaceDiagonalPair(Point to) {
        return isSamePointPair(to, Point.of(3, 7), Point.of(4, 8))
                || isSamePointPair(to, Point.of(4, 8), Point.of(5, 9))
                || isSamePointPair(to, Point.of(5, 7), Point.of(4, 8))
                || isSamePointPair(to, Point.of(4, 8), Point.of(3, 9))
                || isSamePointPair(to, Point.of(3, 7), Point.of(5, 9))
                || isSamePointPair(to, Point.of(5, 7), Point.of(3, 9));
    }

    private boolean isSamePointPair(Point to, Point first, Point second) {
        return (this == first && to == second) || (this == second && to == first);
    }

    private boolean isInChoPalace() {
        return isInRange(CHO_PALACE_MIN_Y, CHO_PALACE_MAX_Y);
    }

    private boolean isInHanPalace() {
        return isInRange(HAN_PALACE_MIN_Y, HAN_PALACE_MAX_Y);
    }

    private boolean isInRange(int minY, int maxY) {
        return x >= PALACE_MIN_X && x <= PALACE_MAX_X
                && y >= minY && y <= maxY;
    }

    private static void addPointsInX(List<Point> pointsAtY, int y) {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            pointsAtY.add(new Point(i, y));
        }
    }

    private static void validateRange(int x, int y) {
        if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 위치 범위입니다.");
        }
    }
}
