package janggi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private static final int BOARD_WIDTH = 9;
    private static final int BOARD_HEIGHT = 10;
    private static final List<List<Point>> CACHE;

    private final int x;
    private final int y;

    static {
        List<List<Point>> points = new ArrayList<>();
        for(int i = 0; i < BOARD_HEIGHT; i++) {
            List<Point> row = new ArrayList<>();
            addX(row, i);
            points.add(row);
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

    public static List<Point> getRow(int y) {
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

    private static void addX(List<Point> row, int y) {
        for(int i = 0; i < BOARD_WIDTH; i++) {
            row.add(new Point(i, y));
        }
    }

    private static void validateRange(int x, int y) {
        if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
            throw new IllegalArgumentException("올바르지 않은 위치 범위입니다.");
        }
    }
}
