package janggi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private static final List<List<Point>> CACHE;

    private final int x;
    private final int y;

    static {
        List<List<Point>> temp = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
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

    public static int getPathX(Point from, Point to) {
        return to.getX() - from.getX();
    }

    public static int getPathY(Point from, Point to) {
        return to.getY() - from.getY();
    }

    private static void addX(List<Point> row, int y) {
        for(int i = 0; i < 9; i++) {
            row.add(new Point(i, y));
        }
    }
}
