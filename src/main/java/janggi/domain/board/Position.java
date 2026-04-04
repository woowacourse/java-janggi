package janggi.domain.board;

import java.util.Optional;

public record Position(int row, int column) {
    private static final String ERROR_OUT_OF_BOUNDS = "[ERROR] %d~%d행, %d~%d열 범위를 벗어날 수 없습니다.";

    public static final int BOARD_MAX_ROW = 9;
    public static final int BOARD_MIN_ROW = 0;
    public static final int BOARD_MAX_COLUMN = 8;
    public static final int BOARD_MIN_COLUMN = 0;

    // 궁성 공통 열 범위
    private static final int PALACE_MIN_COLUMN = 3;
    private static final int PALACE_MAX_COLUMN = 5;

    // 한 진영 궁성 행 범위
    private static final int TOP_PALACE_MIN_ROW = 0;
    private static final int TOP_PALACE_MAX_ROW = 2;

    // 초 진영 궁성 행 범위
    private static final int BOTTOM_PALACE_MIN_ROW = 7;
    private static final int BOTTOM_PALACE_MAX_ROW = 9;

    // 궁성 정중앙 좌표
    private static final int PALACE_CENTER_COLUMN = 4;
    private static final int TOP_PALACE_CENTER_ROW = 1;
    private static final int BOTTOM_PALACE_CENTER_ROW = 8;

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

    public boolean isPalace() {
        boolean isTopPalace = (row >= TOP_PALACE_MIN_ROW && row <= TOP_PALACE_MAX_ROW)
                && (column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN);

        boolean isBottomPalace = (row >= BOTTOM_PALACE_MIN_ROW && row <= BOTTOM_PALACE_MAX_ROW)
                && (column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN);

        return isTopPalace || isBottomPalace;
    }

    public boolean isPalaceCenter() {
        return (row == TOP_PALACE_CENTER_ROW && column == PALACE_CENTER_COLUMN) ||
                (row == BOTTOM_PALACE_CENTER_ROW && column == PALACE_CENTER_COLUMN);
    }
}
