package domain.coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordination {

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
        return other.differentColumn(this.column);
    }

    private int differentColumn(Column other) {
        return this.column.different(other);
    }

    public int differentRow(Coordination other) {
        return other.differentRow(this.row);
    }

    private int differentRow(Row other) {
        return this.row.different(other);
    }

    public boolean isSameRowDifferentColumn(Coordination other) {
        boolean isRowSame = other.isSameRow(this.row);
        boolean isSameColumn = other.isSameColumn(this.column);
        return isRowSame && !isSameColumn;
    }

    public boolean isSameColumnDifferentRow(Coordination other) {
        boolean isRowSame = other.isSameRow(this.row);
        boolean isSameColumn = other.isSameColumn(this.column);
        return !isRowSame && isSameColumn;
    }

    private boolean isSameRow(Row other) {
        return this.row.equals(other);
    }

    private boolean isSameColumn(Column other) {
        return this.column.equals(other);
    }

    public List<Integer> coordination() {
        return List.of(column.index(), row.index());
    }

    public boolean isInRange(int minColumn, int maxColumn, int minRow, int maxRow) {
        int columnIndex = column.index();
        int rowIndex = row.index();
        return columnIndex >= minColumn
                && columnIndex <= maxColumn
                && rowIndex >= minRow
                && rowIndex <= maxRow;
    }

    public List<Coordination> betweenRowCoordination(Coordination other) {
        List<Coordination> coordinations = new ArrayList<>();
        List<Row> rows = other.betweenRows(this.row);
        for (Row row : rows) {
            coordinations.add(new Coordination(this.column, row));
        }
        return coordinations;
    }

    public List<Coordination> betweenColumnCoordination(Coordination other) {
        List<Coordination> coordinations = new ArrayList<>();
        List<Column> columns = other.betweenColumns(this.column);
        for (Column column : columns) {
            coordinations.add(new Coordination(column, this.row));
        }
        return coordinations;
    }

    private List<Row> betweenRows(Row row) {
        return this.row.between(row);
    }

    private List<Column> betweenColumns(Column column) {
        return this.column.between(column);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordination that = (Coordination) o;
        return Objects.equals(column, that.column) && Objects.equals(row, that.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
