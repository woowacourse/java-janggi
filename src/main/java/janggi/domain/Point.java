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
        for(int i = 0; i < 9; i++) {
            List<Point> y = new ArrayList<>();
            addX(y, i);
            temp.add(y);
        }
        CACHE = Collections.unmodifiableList(temp);
    }

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return CACHE.get(x).get(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private static void addX(List<Point> y, int x) {
        for(int i = 0; i < 10; i++) {
            y.add(new Point(x, i));
        }
    }
}
