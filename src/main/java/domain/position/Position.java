package domain.position;

import static domain.board.BoardPolicy.MAX_COLUMN;
import static domain.board.BoardPolicy.MAX_ROW;
import static domain.board.BoardPolicy.MIN_COLUMN;
import static domain.board.BoardPolicy.MIN_ROW;

import domain.board.BoardPolicy;
import domain.place.moveStrategy.Direction;
import java.util.Objects;
import java.util.Optional;

public class Position {

    private final Row row;
    private final Column column;

    public Position(int row, int column) {
        this.row = new Row(row);
        this.column = new Column(column);
    }

    public int getRow() {
        return row.row();
    }

    public int getColumn() {
        return column.column();
    }

    public Optional<Position> moveIfInBounds(Direction direction) {
        int currentRow = getRow() + direction.getRow();
        int currentColumn = getColumn() + direction.getColumn();

        if (BoardPolicy.isOutBoundPosition(currentRow, currentColumn)) {
            return Optional.empty();
        }

        return Optional.of(new Position(currentRow, currentColumn));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position position)) {
            return false;
        }
        return row.equals(position.row) && column.equals(position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

}
