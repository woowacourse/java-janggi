package janggi.model.position;

import java.util.ArrayList;
import java.util.List;

public record Position(
        Row row,
        Column column
) {
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
}
