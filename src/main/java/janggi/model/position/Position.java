package janggi.model.position;

import janggi.model.Team;
import java.util.ArrayList;
import java.util.List;

public record Position(
        Row row,
        Column column
) {
    private static final Position CHO_PALACE_CENTER = new Position(Row.NINE, Column.FIVE);
    private static final Position HAN_PALACE_CENTER = new Position(Row.TWO, Column.FIVE);
    
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
        return isInPalaceOf(CHO_PALACE_CENTER) || isInPalaceOf(HAN_PALACE_CENTER);
    }

    public boolean isInPalaceOf(Team team) {
        if (team == Team.CHO) {
            return isInPalaceOf(CHO_PALACE_CENTER);
        }
        return isInPalaceOf(HAN_PALACE_CENTER);
    }

    private boolean isInPalaceOf(Position center) {
        return Math.abs(this.row.getDistance(center.row)) <= 1
                && Math.abs(this.column.getDistance(center.column)) <= 1;
    }

    public boolean isOnPalaceDiagonal() {
        return isOnDiagonalOf(CHO_PALACE_CENTER) || isOnDiagonalOf(HAN_PALACE_CENTER);
    }

    private boolean isOnDiagonalOf(Position center) {
        int rowDistance = Math.abs(this.row.getDistance(center.row));
        int columnDistance = Math.abs(this.column.getDistance(center.column));
        return (rowDistance == columnDistance) && rowDistance <= 1;
    }
}
