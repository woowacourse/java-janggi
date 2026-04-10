package domain;

import java.util.Objects;

public class Position {

    private static final int MAX_ROWS = 10;
    private static final int MAX_COLUMNS = 9;

    private final int rows; //행 10
    private final int columns; //열 9

    public Position(int rows, int columns) {
        validatePosition(rows, columns);
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Position toRelative() {
        if (isInsideHanPalace()) {
            return new Position(rows, columns - 3);
        } else if (isInsideChoPalace()) {
            return new Position(rows - 7, columns - 3);
        }
        throw new IllegalArgumentException("[ERROR] 좌표가 궁성 내에 위치해 있지 않습니다.");
    }

    public boolean isSamePosition(Position otherPosition) {
        return this.equals(otherPosition);
    }

    public boolean isInsidePalace() {
        return isInsideHanPalace() || isInsideChoPalace();
    }

    public boolean isInsideHanPalace() {
        return rows >= 0 && rows <= 2 && isInsidePalaceColumns();
    }

    public boolean isInsideChoPalace() {
        return rows >= 7 && rows <= 9 && isInsidePalaceColumns();
    }

    private boolean isInsidePalaceColumns() {
        return columns >= 3 && columns <= 5;
    }

    private static void validatePosition(int rows, int columns) {
        if (rows < 0 || rows >= MAX_ROWS || columns < 0 || columns >= MAX_COLUMNS) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않은 위치입니다." + rows + ", " + columns);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return rows == position.rows && columns == position.columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, columns);
    }
}
