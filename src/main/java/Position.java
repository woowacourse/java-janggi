import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public int rowDifference(Position other) {
        return other.rowValue() - row.getValue();
    }

    public int columnDifference(Position other) {
        return other.columnValue() - column.getValue();
    }

    public int rowValue() {
        return row.getValue();
    }

    public int columnValue() {
        return column.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) return false;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }

    public Position divideBy(Position other, int divisor) {
        int newRow = row.getValue() + rowDifference(other) / divisor;
        int newColumn = column.getValue() + columnDifference(other) / divisor;

        return new Position(Row.from(newRow), Column.from(newColumn));
    }

    public List<Position> getBetweenPositions(Position other) {

        //TODO - 리팩토링

        int rowDifference = rowDifference(other);
        int columnDifference = columnDifference(other);

        int rowDir = 0;
        int columnDir =0;

        if(rowDifference != 0) {
            rowDir = rowDifference / Math.abs(rowDifference);
        }
        if(columnDifference != 0) {
            columnDir = columnDifference / Math.abs(columnDifference);
        }

        Position currentPosition = new Position(row, column);
        List<Position> positions = new ArrayList<>();

        while(true) {
            int newRow = currentPosition.rowValue() + rowDir;
            int newCol = currentPosition.columnValue() + columnDir;
            currentPosition = new Position(Row.from(newRow), Column.from(newCol));
            if(currentPosition.equals(other)) {
                break;
            }
            positions.add(currentPosition);
        }
        return positions;
    }
}
