package janggi.position;

public record Position(Row row, Column column) {

    public Position move(int rowMovement, int columnMovement) {
        return new Position(row.move(rowMovement), column.move(columnMovement));
    }

    public boolean isSameRow(Position position) {
        return position.row.equals(row);
    }

    public boolean isSameColumn(Position position) {
        return position.column.equals(column);
    }

    public boolean isOutOfBoards() {
        return row().isOutOfBounds() || column().isOutOfBounds();
    }

    public boolean isVerticalFromPosition(Position position) {
        return isSameColumn(position);
    }

    public boolean isHorizontalFromPosition(Position position) {
        return isSameRow(position);
    }

    public int getRow() {
        return row.value();
    }

    public int getColumn() {
        return column.value();
    }

}
