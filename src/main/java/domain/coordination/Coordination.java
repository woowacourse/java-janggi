package domain.coordination;

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

    public int differentColumn(Coordination to) {
        return to.differentColumn(this.column);
    }

    private int differentColumn(Column column) {
        return this.column.different(column);
    }

    public int differentRow(Coordination to) {
        return to.differentRow(this.row);
    }

    private int differentRow(Row row) {
        return this.row.different(row);
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
