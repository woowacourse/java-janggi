package janggi.model.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record Position(
        Row row,
        Column column
) {
    private static final Set<Position> CHO_PALACE = Set.of(
            new Position(Row.EIGHT, Column.FOUR),
            new Position(Row.EIGHT, Column.FIVE),
            new Position(Row.EIGHT, Column.SIX),
            new Position(Row.NINE, Column.FOUR),
            new Position(Row.NINE, Column.FIVE),
            new Position(Row.NINE, Column.SIX),
            new Position(Row.ZERO, Column.FOUR),
            new Position(Row.ZERO, Column.FIVE),
            new Position(Row.ZERO, Column.SIX)
    );

    private static final Set<Position> HAN_PALACE = Set.of(
            new Position(Row.ONE, Column.FOUR),
            new Position(Row.ONE, Column.FIVE),
            new Position(Row.ONE, Column.SIX),
            new Position(Row.TWO, Column.FOUR),
            new Position(Row.TWO, Column.FIVE),
            new Position(Row.TWO, Column.SIX),
            new Position(Row.THREE, Column.FOUR),
            new Position(Row.THREE, Column.FIVE),
            new Position(Row.THREE, Column.SIX)
    );

    public PositionPath moveDiagonal(DiagonalDelta diagonalDelta) {
        List<Position> positions = new ArrayList<>();
        positions.add(this);
        addDiagonalPositions(positions, diagonalDelta);
        return new PositionPath(positions);
    }

    private void addDiagonalPositions(List<Position> positions, DiagonalDelta diagonalDelta) {
        Row nextRow = row;
        Column nextColumn = column;
        for (int i = 0; i < diagonalDelta.getCountOfUnitDiagonal(); i++) {
            nextRow = nextRow.moved(diagonalDelta.getRowUnitDistance());
            nextColumn = nextColumn.moved(diagonalDelta.getColumnUnitDistance());
            positions.add(new Position(nextRow, nextColumn));
        }
    }

    public PositionPath moveHorizontal(int distance) {
        Position to = new Position(
                row,
                Column.of(column.getValue() + distance)
        );

        if (!this.row.equals(to.row)) {
            throw new IllegalArgumentException("같은 행이 아닙니다.");
        }
        List<Column> columns = this.column.to(to.column);
        return new PositionPath(columns.stream().map(column -> new Position(this.row, column)).toList());
    }

    public PositionPath moveVertical(int distance) {
        Position to = new Position(
                Row.of(row.getValue() + distance),
                column
        );

        if (!this.column.equals(to.column)) {
            throw new IllegalArgumentException("같은 열이 아닙니다.");
        }

        List<Row> rows = this.row.to(to.row);
        return new PositionPath(rows.stream().map(row -> new Position(row, this.column)).toList());
    }

    public int getRowDistance(Position other) {
        return this.row.getDistance(other.row);
    }

    public int getColumnDistance(Position other) {
        return this.column.getDistance(other.column);
    }

    public boolean isSameRow(Position other) {
        return this.row == other.row;
    }

    public boolean isSameColumn(Position other) {
        return this.column == other.column;
    }

    public boolean isInPalace() {
        return CHO_PALACE.contains(this) || HAN_PALACE.contains(this);
    }
}
