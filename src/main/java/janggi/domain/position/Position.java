package janggi.domain.position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record Position(
        Row row,
        Column column
) {

    private static final Map<String, Position> POSITIONS = new HashMap<>();

    static {
        for (Row row : Row.values()) {
            for (Column column : Column.values()) {
                POSITIONS.put(toKey(row, column), new Position(row, column));
            }
        }
    }

    public static Position from(int row, int column) {
        return POSITIONS.get(toKey(Row.of(row), Column.of(column)));
    }

    private static String toKey(Row row, Column column) {
        return row + "," + column;
    }

    // TODO: find all position 과 같이 방향 전체를 담는다는 의미를 강조
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

    // TODO: find -> next 메서드 명 변경 nextPositionByDirection?
    public Optional<Position> findPositionByDirection(Direction dir) {
        try {
            return Optional.of(this.add(dir.row(), dir.column()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private Position add(int row, int column) {
        Row nextRow = this.row.add(row);
        Column nextColumn = this.column.add(column);
        return POSITIONS.get(toKey(nextRow, nextColumn));
    }

}
