package janggi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private static final List<List<Point>> CACHE;
    private static final int ROW_RANGE = 10;
    private static final int COLUMN_RANGE = 9;

    private final int col;
    private final int row;

    static {
        List<List<Point>> temp = new ArrayList<>();
        for(int i = 0; i < ROW_RANGE; i++) {
            List<Point> rows = new ArrayList<>();
            addColumn(rows, i);
            temp.add(rows);
        }
        CACHE = Collections.unmodifiableList(temp);
    }

    private Point(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public static Point of(int col, int row) {
        if (col < 0 || col >= COLUMN_RANGE || row < 0 || row >= ROW_RANGE) {
            throw new IllegalArgumentException("[ERROR] 장기판 범위를 벗어났습니다.");
        }
        return CACHE.get(row).get(col);
    }

    public int calculatePathColumn(Point from) {
        return this.col - from.col;
    }

    public int calculatePathRow(Point from) {
        return this.row - from.row;
    }

    public int getColumn() {
        return col;
    }

    public int getRow() {
        return row;
    }

    private static void addColumn(List<Point> row, int col) {
        for(int i = 0; i < COLUMN_RANGE; i++) {
            row.add(new Point(i, col));
        }
    }
}
