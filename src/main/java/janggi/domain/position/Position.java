package janggi.domain.position;

import janggi.domain.dynasty.Dynasty;
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
    private static final Map<Position, List<Direction>> DIAGONAL = new HashMap<>();
    private static final int MIN_PALACE_COLUMN = 4;
    private static final int MAX_PALACE_COLUMN = 6;
    private static final int FLOOR_OF_CHO_PALACE_ROW = 3;
    private static final int CEILING_OF_HAN_PALACE_ROW = 8;

    static {
        for (Row row : Row.values()) {
            for (Column column : Column.values()) {
                POSITIONS.put(toKey(row, column), new Position(row, column));
            }
        }
    }

    static {
        DIAGONAL.put(Position.from(1, 4), List.of(Direction.SOUTHEAST));
        DIAGONAL.put(Position.from(1, 6), List.of(Direction.SOUTHWEST));
        DIAGONAL.put(Position.from(2, 5), List.of(Direction.valuesFourDiagonalDirection()));
        DIAGONAL.put(Position.from(3, 4), List.of(Direction.NORTHEAST));
        DIAGONAL.put(Position.from(3, 6), List.of(Direction.NORTHWEST));
        DIAGONAL.put(Position.from(8, 4), List.of(Direction.SOUTHEAST));
        DIAGONAL.put(Position.from(8, 6), List.of(Direction.SOUTHWEST));
        DIAGONAL.put(Position.from(9, 5), List.of(Direction.valuesFourDiagonalDirection()));
        DIAGONAL.put(Position.from(10, 4), List.of(Direction.NORTHEAST));
        DIAGONAL.put(Position.from(10, 6), List.of(Direction.NORTHWEST));
    }

    public static Position from(int row, int column) {
        return POSITIONS.get(toKey(Row.of(row), Column.of(column)));
    }

    private static String toKey(Row row, Column column) {
        return row + "," + column;
    }

    public List<Position> findAllPositionsByDirection(Direction dir) {
        List<Position> positions = new ArrayList<>();
        Optional<Position> current = nextPositionByDirection(dir);

        while (current.isPresent()) {
            Position position = current.get();
            positions.add(position);
            current = position.nextPositionByDirection(dir);
        }
        return positions;
    }

    public Optional<Position> nextPositionByDirection(Direction dir) {
        try {
            return Optional.of(this.add(dir.row(), dir.column()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public List<Direction> directions() {
        List<Direction> directions = new ArrayList<>(List.of(Direction.valuesFourDirection()));
        if (canMoveDiagonal()) {
            directions.addAll(DIAGONAL.get(this));
        }
        return directions;
    }

    public boolean isPalace(Dynasty dynasty) {
        if (column.column() < MIN_PALACE_COLUMN || column.column() > MAX_PALACE_COLUMN) {
            return false;
        }
        if (dynasty.equals(Dynasty.HAN)) {
            return row.row() >= CEILING_OF_HAN_PALACE_ROW;
        }
        return row.row() <= FLOOR_OF_CHO_PALACE_ROW;
    }

    private boolean canMoveDiagonal() {
        return DIAGONAL.containsKey(this);
    }

    private Position add(int row, int column) {
        Row nextRow = this.row.add(row);
        Column nextColumn = this.column.add(column);
        return POSITIONS.get(toKey(nextRow, nextColumn));
    }

}
