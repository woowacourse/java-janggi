package janggi.position;

import janggi.rule.MoveVector;
import java.util.Objects;

public final class Position {

    private final Row row;
    private final Column column;

    public Position(final Row row, final Column column) {
        this.row = row;
        this.column = column;
    }

    public MoveVector calculateVectorDiff(final Position another) {
        final int rowDiff = row.ordinal() - another.getRowValue();
        final int columnDiff = column.ordinal() - another.getColumnValue();
        return new MoveVector(rowDiff, columnDiff);
    }

    public Position add(final MoveVector vector) {
        return new Position(row.add(vector.dy()), column.add(vector.dx()));
    }

    public int getRowValue() {
        return row.ordinal();
    }

    public int getColumnValue() {
        return column.ordinal();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
