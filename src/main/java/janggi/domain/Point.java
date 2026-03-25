package janggi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private int column;
    private int row;
    private static final List<List<Point>> CACHE;

    static {
        List<List<Point>> temp = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            List<Point> row = new ArrayList<>();
            addColumn(row, i);
            temp.add(row);
        }
        CACHE = Collections.unmodifiableList(temp);
    }

    private Point(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public static Point of(int column, int row) {
        return CACHE.get(column).get(row);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    private static void addColumn(List<Point> row, int column) {
        for(int i = 0; i < 9; i++) {
            row.add(new Point(column, i));
        }
    }
}
