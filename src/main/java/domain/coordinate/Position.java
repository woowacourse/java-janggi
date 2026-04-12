package domain.coordinate;

import java.util.List;
import java.util.Map;

public record Position(int col, int row) {

    private static final int COL_SIZE = 10;
    private static final int ROW_SIZE = 9;
    private static final int POSITION_THRESHOLD = 0;
    private static final int PALACE_ROW_START = 3;
    private static final int PALACE_ROW_END = 5;
    private static final int TOP_PALACE_COL_START = 0;
    private static final int TOP_PALACE_COL_END = 2;
    private static final int BOTTOM_PALACE_COL_START = 7;
    private static final int BOTTOM_PALACE_COL_END = 9;

    private static final Map<Position, Direction> PALACE_EDGE_POSITIONS = Map.ofEntries(
            Map.entry(new Position(0, 3), Direction.DOWN_RIGHT),
            Map.entry(new Position(0, 5), Direction.DOWN_LEFT),
            Map.entry(new Position(2, 3), Direction.UP_RIGHT),
            Map.entry(new Position(2, 5), Direction.UP_LEFT),

            Map.entry(new Position(7, 3), Direction.DOWN_RIGHT),
            Map.entry(new Position(7, 5), Direction.DOWN_LEFT),
            Map.entry(new Position(9, 3), Direction.UP_RIGHT),
            Map.entry(new Position(9, 5), Direction.UP_LEFT)
    );
    private static final Map<Position, List<Direction>> PALACE_CENTER_POSITIONS = Map.ofEntries(
            Map.entry(new Position(1, 4),
                    List.of(Direction.DOWN_RIGHT, Direction.DOWN_LEFT, Direction.UP_RIGHT, Direction.UP_LEFT)),
            Map.entry(new Position(8, 4),
                    List.of(Direction.DOWN_RIGHT, Direction.DOWN_LEFT, Direction.UP_RIGHT, Direction.UP_LEFT))
    );

    public static Position of(int col, int row) {
        validateRange(col, row);
        return new Position(col, row);
    }

    public static Position unsafe(int col, int row) {
        return new Position(col, row);
    }

    public Position nextPosition(Direction direction) {
        return Position.unsafe(col + direction.getCol(), row + direction.getRow());
    }

    public boolean isValidRange() {
        return col >= POSITION_THRESHOLD && col < COL_SIZE
                && row >= POSITION_THRESHOLD && row < ROW_SIZE;
    }

    public boolean isInPalace() {
        return (row >= PALACE_ROW_START && row <= PALACE_ROW_END) &&
                ((col >= TOP_PALACE_COL_START && col <= TOP_PALACE_COL_END) ||
                        (col >= BOTTOM_PALACE_COL_START && col <= BOTTOM_PALACE_COL_END));
    }

    public boolean isInPalaceEdgePosition() {
        return PALACE_EDGE_POSITIONS.containsKey(Position.of(col, row));
    }

    public boolean isInPalaceCenterPosition() {
        return PALACE_CENTER_POSITIONS.containsKey(Position.of(col, row));
    }

    public Direction getPalaceEdgeDirection() {
        return PALACE_EDGE_POSITIONS.get(Position.of(col, row));
    }

    public List<Direction> getPalaceCenterDirection() {
        return PALACE_CENTER_POSITIONS.get(Position.of(col, row));
    }

    private static void validateRange(int col, int row) {
        if (col < POSITION_THRESHOLD || col >= COL_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 열 좌표: %d (열 좌표는 0 에서 9 사이여야 합니다.)", col));
        }

        if (row < POSITION_THRESHOLD || row >= ROW_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 행 좌표: %d (행 좌표는 0 에서 8 사이여야 합니다.)", row));
        }
    }
}
