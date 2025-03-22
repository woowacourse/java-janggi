package model;


import java.util.List;
import java.util.Objects;

public class Position {

    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position(List<Integer> columnAndRow) {
        this.column = Column.getColumnBy(columnAndRow.getFirst());
        this.row = Row.getRowBy(columnAndRow.getLast());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
