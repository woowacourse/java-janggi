package janggi.domain.board;

import janggi.domain.game.Side;
import java.util.Collections;
import java.util.List;
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
        return isWithinTopPalace() || isWithinBottomPalace();
    }

    private boolean isWithinTopPalace() {
        return (row >= TOP_PALACE_MIN_ROW && row <= TOP_PALACE_MAX_ROW)
                && (column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN);
    }

    private boolean isWithinBottomPalace() {
        return (row >= BOTTOM_PALACE_MIN_ROW && row <= BOTTOM_PALACE_MAX_ROW)
                && (column >= PALACE_MIN_COLUMN && column <= PALACE_MAX_COLUMN);
    }

    public boolean isPalaceCenter() {
        return (row == TOP_PALACE_CENTER_ROW && column == PALACE_CENTER_COLUMN) ||
                (row == BOTTOM_PALACE_CENTER_ROW && column == PALACE_CENTER_COLUMN);
    }

    public boolean isPalaceCorner() {
        boolean isTopCorner = (row == TOP_PALACE_MIN_ROW || row == TOP_PALACE_MAX_ROW)
                && (column == PALACE_MIN_COLUMN || column == PALACE_MAX_COLUMN);

        boolean isBottomCorner = (row == BOTTOM_PALACE_MIN_ROW || row == BOTTOM_PALACE_MAX_ROW)
                && (column == PALACE_MIN_COLUMN || column == PALACE_MAX_COLUMN);

        return isTopCorner || isBottomCorner;
    }

    public List<Direction> getValidPalaceDiagonals() {
        if (isPalaceCenter()) {
            return List.of(Direction.NE, Direction.NW, Direction.SE, Direction.SW);
        }

        // 좌상단 꼭짓점
        if ((row == TOP_PALACE_MIN_ROW || row == BOTTOM_PALACE_MIN_ROW) && column == PALACE_MIN_COLUMN) {
            return List.of(Direction.SE);
        }
        // 우상단 꼭짓점
        if ((row == TOP_PALACE_MIN_ROW || row == BOTTOM_PALACE_MIN_ROW) && column == PALACE_MAX_COLUMN) {
            return List.of(Direction.SW);
        }
        // 좌하단 꼭짓점
        if ((row == TOP_PALACE_MAX_ROW || row == BOTTOM_PALACE_MAX_ROW) && column == PALACE_MIN_COLUMN) {
            return List.of(Direction.NE);
        }
        // 우하단 꼭짓점
        if ((row == TOP_PALACE_MAX_ROW || row == BOTTOM_PALACE_MAX_ROW) && column == PALACE_MAX_COLUMN) {
            return List.of(Direction.NW);
        }

        return Collections.emptyList();
    }

    // 상대 궁성인지 판단
    public boolean isOpponentPalace(Side side) {
        if (side == Side.CHO) {
            return isWithinTopPalace();
        }
        return isWithinBottomPalace();
    }
}
