package janggi.model.position.absolute;

public record Position(
        Row row,
        Column column
) {
    public int getRowDiff(Position other) {
        return this.row.getDistanceTo(other.row);
    }

    public int getColumnDiff(Position other) {
        return this.column.getDistanceTo(other.column);
    }

    public int getDistanceTo(Position other) {
        int rowDistance = Math.abs(this.row.getDistanceTo(other.row));
        int columnDistance = Math.abs(this.column.getDistanceTo(other.column));

        if (rowDistance > columnDistance) {
            return rowDistance;
        }

        return columnDistance;
    }

    public boolean isSameRow(Position other) {
        return this.row == other.row;
    }

    public boolean isSameColumn(Position other) {
        return this.column == other.column;
    }

    public boolean isLocatedNorthOf(Position other) {
        return getRowDiff(other) < 0;
    }
}
