package janggi;

public record Position(Row row, Column column) {

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
    }

    public MoveVector getDiff(final Position another) {
        final int rowDiff = row.getValue() - another.getRowValue();
        final int columnDiff = column.getValue() - another.getColumnValue();
        return new MoveVector(rowDiff, columnDiff);
    }

    public Position add(final MoveVector vector) {
        return new Position(row.add(vector.dy()), column.add(vector.dx()));
    }
}
