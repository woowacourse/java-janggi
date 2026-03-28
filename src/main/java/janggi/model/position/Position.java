package janggi.model.position;

import java.util.ArrayList;
import java.util.List;

public record Position(
        Row row,
        Column column
) {
    public PositionPath moveDiagonal(DiagonalDelta diagonalDelta) {
        int rowUnitDistance = diagonalDelta.getRowUnitDistance();
        int columnUnitDistance = diagonalDelta.getColumnUnitDistance();

        Row nextRow = row;
        Column nextColumn = column;

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(nextRow, nextColumn));

        for (int i = 0; i < diagonalDelta.getCountOfUnitDiagonal(); i++) {
            nextRow = nextRow.moved(rowUnitDistance);
            nextColumn = nextColumn.moved(columnUnitDistance);
            positions.add(new Position(nextRow, nextColumn));
        }

        return new PositionPath(positions);
    }

    public PositionPath moveHorizontal(int distance) {
        int adjustValue = 1;
        Position to = new Position(
                row,
                Column.of((column.ordinal() + adjustValue) + distance)
        );

        if (!this.row.equals(to.row)) {
            throw new IllegalArgumentException("같은 행이 아닙니다.");
        }
        List<Column> columns = this.column.to(to.column);
        return new PositionPath(columns.stream().map(column -> new Position(this.row, column)).toList());
    }

    public PositionPath moveVertical(int distance) {
        int adjustValue = 1;

        Position to = new Position(
                Row.of((row.ordinal() + adjustValue) + distance),
                column
        );

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
