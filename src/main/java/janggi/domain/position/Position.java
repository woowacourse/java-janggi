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

    public Optional<Position> findPositionByDirection(Direction dir) {
        try {
            return Optional.of(this.add(dir.row(), dir.column()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Position add(int row, int column) {
        return new Position(this.row.add(row), this.column.add(column));
    }

}
