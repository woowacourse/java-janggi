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

    public List<Position> north(Position from) {
        List<Position> northPositions = new ArrayList<>();
        while (true) {
            try {
                from = from.add(-1, 0);
                northPositions.add(from);
            } catch (IllegalArgumentException e) {
                break;
            }
        }
        return northPositions;
    }

}
