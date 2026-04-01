package janggi.domain.position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public record Position(
        Row row,
        Column column
) {

    public static Position from(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public List<Position> findAllPositionsByDirection(Direction dir) {
        List<Position> positions = new ArrayList<>();
        Position cur = this;
        while (cur.isOffsetWithinBounds(dir.row(), dir.column())) {
            cur = cur.add(dir.row(), dir.column());
            positions.add(cur);
        }
        return positions;
    }

    public Optional<Position> findOnePositionByDirection(Direction dir) {

        if(isOffsetWithinBounds(dir.row(), dir.column())) {
            return Optional.of(this.add(dir.row(), dir.column()));
        }

        return Optional.empty();
    }


    private Position add(int row, int column) {
        return new Position(this.row.add(row), this.column.add(column));
    }

    private boolean isOffsetWithinBounds(int row, int column) {
        return this.row.isOffsetWithinBounds(row) &&
                this.column.isOffsetWithinBounds(column);
    }

}
