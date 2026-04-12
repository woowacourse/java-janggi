package domain.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Position(
        int column,
        int row
) {

    private static final int MIN_COLUMN_RANGE = 1;
    private static final int MAX_COLUMN_RANGE = 10;
    private static final int MIN_ROW_RANGE = 1;
    private static final int MAX_ROW_RANGE = 9;

    private static final Position HAN_PALACE_LEFT_UP = Position.of(1, 4);
    private static final Position HAN_PALACE_RIGHT_DOWN = Position.of(3, 6);
    private static final Position CHO_PALACE_LEFT_UP = Position.of(8, 4);
    private static final Position CHO_PALACE_RIGHT_DOWN = Position.of(10, 6);

    private static final Map<Position, List<Position>> DIAGONAL_EDGES = createDiagonalEdges();

    private static Map<Position, List<Position>> createDiagonalEdges() {
        Map<Position, List<Position>> edges = new HashMap<>();

        connect(edges, Position.of(1, 4), Position.of(2, 5));
        connect(edges, Position.of(1, 6), Position.of(2, 5));
        connect(edges, Position.of(3, 4), Position.of(2, 5));
        connect(edges, Position.of(3, 6), Position.of(2, 5));

        connect(edges, Position.of(8, 4), Position.of(9, 5));
        connect(edges, Position.of(8, 6), Position.of(9, 5));
        connect(edges, Position.of(10, 4), Position.of(9, 5));
        connect(edges, Position.of(10, 6), Position.of(9, 5));

        return Collections.unmodifiableMap(edges);
    }

    private static void connect(final Map<Position, List<Position>> map, final Position from, final Position to) {
        map.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        map.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
    }


    public static Position of(final int column, final int row) {
        return new Position(column, row);
    }

    public Position move(final Direction direction) {
        return Position.of(column + direction.column(), row + direction.row());
    }

    public boolean isInsideBoard() {
        return column >= MIN_COLUMN_RANGE && column <= MAX_COLUMN_RANGE
                && row >= MIN_ROW_RANGE && row <= MAX_ROW_RANGE;
    }

    public Position mirror() {
        int mirroredColumn = MAX_COLUMN_RANGE - column + 1;
        int mirroredRow = MAX_ROW_RANGE - row + 1;
        return Position.of(mirroredColumn, mirroredRow);
    }

    public List<Position> getDiagonalPositions() {
        return DIAGONAL_EDGES.getOrDefault(this, List.of());
    }

    public boolean isDiagonalConnected(final Position other) {
        return DIAGONAL_EDGES.containsKey(this) && DIAGONAL_EDGES.get(this).contains(other);
    }

    public boolean isInsidePalace() {
        return isInsideChoPalace() || isInsideHanPalace();
    }

    public boolean isInsideChoPalace() {
        return CHO_PALACE_LEFT_UP.column <= column && column <= CHO_PALACE_RIGHT_DOWN.column
                && CHO_PALACE_LEFT_UP.row <= row && row <= CHO_PALACE_RIGHT_DOWN.row;
    }

    public boolean isInsideHanPalace() {
        return HAN_PALACE_LEFT_UP.column <= column && column <= HAN_PALACE_RIGHT_DOWN.column
                && HAN_PALACE_LEFT_UP.row <= row && row <= HAN_PALACE_RIGHT_DOWN.row;
    }
}
