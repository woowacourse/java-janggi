package janggiGame.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dot {
    private static final int MIN_ROW_RANGE = 0;
    private static final int MAX_ROW_RANGE = 8;
    private static final int MIN_COLUMN_RANGE = 0;
    private static final int MAX_COLUMN_RANGE = 9;
    private static final List<Dot> dots = createDots();

    private final int row;
    private final int column;

    public Dot(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    private static List<Dot> createDots() {
        List<Dot> dots = new ArrayList<>();
        for (int column = MAX_COLUMN_RANGE; column >= MIN_COLUMN_RANGE; column--) {
            for (int row = MIN_ROW_RANGE; row <= MAX_ROW_RANGE; row++) {
                dots.add(new Dot(row, column));
            }
        }
        return dots;
    }

    public static Dot of(final int row, final int column) {
        return dots.stream()
                .filter(d -> d.row == row && d.column == column)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 장기판에 존재하는 좌표가 아닙니다."));
    }

    public static List<Dot> getDots() {
        return List.copyOf(dots);
    }

    public Dot getReverse() {
        return dots.stream()
                .filter(d -> d.row == MAX_ROW_RANGE - this.row)
                .filter(d -> d.column == MAX_COLUMN_RANGE - this.column)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public int calculateRowChange(Dot other) {
        return other.row - this.row;
    }

    public int calculateColumnChange(Dot other) {
        return other.column - this.column;
    }

    public Dot up() {
        return of(row, column + 1);
    }

    public Dot down() {
        return of(row, column - 1);
    }

    public Dot right() {
        return of(row + 1, column);
    }

    public Dot left() {
        return of(row - 1, column);
    }

    public Dot upRight() {
        return of(row + 1, column + 1);
    }

    public Dot upLeft() {
        return of(row - 1, column + 1);
    }

    public Dot downRight() {
        return of(row + 1, column - 1);
    }

    public Dot downLeft() {
        return of(row - 1, column - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dot dot = (Dot) o;
        return Objects.equals(row, dot.row) && Objects.equals(column, dot.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
