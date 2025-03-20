package domain;

import java.util.Objects;

public class Column {
    public static final int MAX_COLUMN = 8;
    public static final int MIN_COLUMN = 0;

    private final int column;

    public Column(int column) {
        validateColumn(column);
        this.column = column;
    }

    private void validateColumn(int column) {
        if (MAX_COLUMN < column || column < MIN_COLUMN) {
            throw new IllegalArgumentException("장기판 열의 범위를 벗어났습니다.");
        }
    }

    public boolean canMoveColumn(int deltaColumn){
        int moveColumn = column + deltaColumn;
        return moveColumn <= MAX_COLUMN && moveColumn >= MIN_COLUMN;
    }

    public Column moveColumn(int deltaColumn){
        return new Column(column + deltaColumn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column1 = (Column) o;
        return column == column1.column;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(column);
    }
}
