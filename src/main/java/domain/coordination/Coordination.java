package domain.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordination {

    private static final String NOT_DIAGONAL_PATH_MESSAGE = "대각선 경로가 아닙니다.";

    private final Column column;
    private final Row row;

    private Coordination(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public static Coordination of(int columnIndex, int rowIndex) {
        return new Coordination(new Column(columnIndex), new Row(rowIndex));
    }

    public Coordination plus(int column, int row) {
        Column plusColumn = this.column.plus(column);
        Row plusRow = this.row.plus(row);
        return new Coordination(plusColumn, plusRow);
    }

    public int differentColumn(Coordination other) {
        return other.column.different(this.column);
    }

    public int differentRow(Coordination other) {
        return other.row.different(this.row);
    }

    public boolean isHorizontal(Coordination other) {
        return this.row.equals(other.row) && !this.column.equals(other.column);
    }

    public boolean isVertical(Coordination other) {
        return !this.row.equals(other.row) && this.column.equals(other.column);
    }

    public boolean isDiagonal(Coordination to) {
        int columnDifferent = Math.abs(this.column.different(to.column));
        int rowDifferent = Math.abs(this.row.different(to.row));
        return columnDifferent == rowDifferent;
    }

    public List<Coordination> diagonalPathTo(Coordination other) {
        List<Column> columnBetween = this.column.between(other.column);
        List<Row> rowBetween = this.row.between(other.row);
        if (columnBetween.size() != rowBetween.size()) {
            throw new IllegalArgumentException(NOT_DIAGONAL_PATH_MESSAGE);
        }
        List<Coordination> coordinations = new ArrayList<>();
        for (int i = 0; i < columnBetween.size(); i++) {
            coordinations.add(new Coordination(columnBetween.get(i), rowBetween.get(i)));
        }
        return coordinations;
    }

    public List<Integer> coordination() {
        return List.of(column.index(), row.index());
    }


    public List<Coordination> verticalPathTo(Coordination other) {
        List<Coordination> coordinations = new ArrayList<>();
        List<Row> rows = this.row.between(other.row);
        for (Row row : rows) {
            coordinations.add(new Coordination(this.column, row));
        }
        return coordinations;
    }

    public List<Coordination> horizontalPathTo(Coordination other) {
        List<Coordination> coordinations = new ArrayList<>();
        List<Column> columns = this.column.between(other.column);
        for (Column column : columns) {
            coordinations.add(new Coordination(column, this.row));
        }
        return coordinations;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordination that = (Coordination) o;
        return Objects.equals(column, that.column) && Objects.equals(row, that.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
