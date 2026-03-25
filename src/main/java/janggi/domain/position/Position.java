package janggi.domain.position;

import java.util.ArrayList;
import java.util.List;

public record Position(
        Row row,
        Column column
) {

    public static Position from(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public Position add(int row, int column) {
        return new Position(this.row.add(row), this.column.add(column));
    }

    public List<Position> findPositionsByDirection(Direction dir) {
        List<Position> positions = new ArrayList<>();
        Position cur = this;
        while (true) {
            try {
                cur = cur.add(dir.row(), dir.column());
                positions.add(cur);
            } catch (IllegalArgumentException e) {
                break;
            }
        }
        return positions;
    }

}
