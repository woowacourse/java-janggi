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
        return other.column.different(this.column);
    }

    public int differentRow(Coordination other) {
        return other.row.different(this.row);
    }

    public boolean isSameRowDifferentColumn(Coordination other) {
        return this.row.equals(other.row) && !this.column.equals(other.column);
    }

    public boolean isSameColumnDifferentRow(Coordination other) {
        return !this.row.equals(other.row) && this.column.equals(other.column);
    }

    public List<Integer> coordination() {
        return List.of(column.index(), row.index());
    }


    public List<Coordination> betweenRowCoordination(Coordination other) {
        List<Coordination> coordinations = new ArrayList<>();
        List<Row> rows = this.row.between(other.row);
        for (Row row : rows) {
            coordinations.add(new Coordination(this.column, row));
        }
        return coordinations;
    }

    public List<Coordination> betweenColumnCoordination(Coordination other) {
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
