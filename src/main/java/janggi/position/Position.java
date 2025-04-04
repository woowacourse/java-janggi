package janggi.position;

import janggi.team.Team;

public record Position(int row, int column) {
    private static final int BOARD_ROW_MIN = 1;
    private static final int BOARD_ROW_MAX = 10;
    private static final int BOARD_COLUMN_MIN = 1;
    private static final int BOARD_COLUMN_MAX = 9;

    private static final int TOP_ROW_OF_CHO_PALACE = 1;
    private static final int BOTTOM_ROW_OF_CHO_PALACE = 3;
    private static final int TOP_ROW_OF_HAN_PALACE =8;
    private static final int BOTTOM_ROW_OF_HAN_PALACE = 10;
    private static final int RIGHT_COLUMN_OF_PALACE = 6;
    private static final int LEFT_COLUMN_OF_PALACE = 4;

    public Position move(int rowMovement, int columnMovement) {
        return new Position(row + rowMovement, column + columnMovement);
    }

    public boolean isSameRow(Position position) {
        return position.row == row;
    }

    public boolean isSameColumn(Position position) {
        return position.column == column;
    }

    public boolean isOutOfBoards() {
        return  (row < BOARD_ROW_MIN || row > BOARD_ROW_MAX) || (column < BOARD_COLUMN_MIN || column > BOARD_COLUMN_MAX);
    }

    public boolean isOutOfPalace() {
        return isOutOfHanPalace() && isOutOfChoPalace();
    }

    private boolean isOutOfHanPalace() {
        return (row < TOP_ROW_OF_HAN_PALACE || row >BOTTOM_ROW_OF_HAN_PALACE) || (column < LEFT_COLUMN_OF_PALACE || column > RIGHT_COLUMN_OF_PALACE);
    }

    private boolean isOutOfChoPalace() {
        return (row < TOP_ROW_OF_CHO_PALACE || row >BOTTOM_ROW_OF_CHO_PALACE) || (column < LEFT_COLUMN_OF_PALACE || column > RIGHT_COLUMN_OF_PALACE);
    }

    public boolean isVerticalFromPosition(Position position) {
        return isSameColumn(position);
    }

    public boolean isHorizontalFromPosition(Position position) {
        return isSameRow(position);
    }

    public boolean isCrossFromPosition(Position position) {
        return Math.abs(position.row - row) == Math.abs(position.column - column);
    }

    public int calculateRowDistance(Position position) {
        return Math.abs(position.row - row);
    }

    public int calculateRowDistance(int row) {
        return Math.abs(this.row - row);
    }

    public int calculateColumnDistance(Position position) {
        return Math.abs(position.column - column);
    }
}
