package janggi.domain.board;

import java.util.Optional;

public record Position(int row, int column) {
    private static final String ERROR_OUT_OF_BOUNDS = "[ERROR] %d~%d행, %d~%d열 범위를 벗어날 수 없습니다.";

    public static final int BOARD_MAX_ROW = 9;
    public static final int BOARD_MIN_ROW = 0;
    public static final int BOARD_MAX_COLUMN = 8;
    public static final int BOARD_MIN_COLUMN = 0;

    public Position {
        if (!isWithinBoard(row, column)) {
            throw new IllegalArgumentException(
                    String.format(ERROR_OUT_OF_BOUNDS, BOARD_MIN_ROW, BOARD_MAX_ROW, BOARD_MIN_COLUMN,
                            BOARD_MAX_COLUMN));
        }
    }

    public Optional<Position> tryMove(Direction direction) {
        int nextRow = direction.getNextRow(this.row);
        int nextColumn = direction.getNextColumn(this.column);

        if (isWithinBoard(nextRow, nextColumn)) {
            return Optional.of(new Position(nextRow, nextColumn));
        }

        return Optional.empty();
    }

    private static boolean isWithinBoard(int row, int column) {
        return (row >= BOARD_MIN_ROW && row <= BOARD_MAX_ROW) &&
                (column >= BOARD_MIN_COLUMN && column <= BOARD_MAX_COLUMN);
    }
}
