package janggi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private static final List<List<Point>> CACHE;
    private static final int COLUMN_RANGE = 10;
    private static final int ROW_RANGE = 9;

    private final int x;
    private final int y;

    static {
        List<List<Point>> temp = new ArrayList<>();
        for(int i = 0; i < COLUMN_RANGE; i++) {
            List<Point> row = new ArrayList<>();
            addX(row, i);
            temp.add(row);
        }
        CACHE = Collections.unmodifiableList(temp);
    }

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        if (x < 0 || x >= ROW_RANGE || y < 0 || y >= COLUMN_RANGE) {
            throw new IllegalArgumentException("[ERROR] 장기판 범위를 벗어났습니다.");
        }
        return CACHE.get(y).get(x);
    }

    public int calculatePathX(Point from) {
        return this.x - from.x;
    }

    public int calculatePathY(Point from) {
        return this.y - from.y;
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

    private static void addX(List<Point> row, int y) {
        for(int i = 0; i < ROW_RANGE; i++) {
            row.add(new Point(i, y));
        }
    }
}
