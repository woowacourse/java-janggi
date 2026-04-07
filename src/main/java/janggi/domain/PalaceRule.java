package janggi.domain;

import janggi.domain.vo.Position;

public class PalaceRule {

    private static final int HAN_MIN_ROW = 0;
    private static final int HAN_MAX_ROW = 2;
    private static final int CHO_MIN_ROW = 7;
    private static final int CHO_MAX_ROW = 9;
    private static final int MIN_COL = 3;
    private static final int MAX_COL = 5;

    private static final Position HAN_CENTER = new Position(1, 4);
    private static final Position CHO_CENTER = new Position(8, 4);

    public static boolean isInsidePalace(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        if (col < MIN_COL || col > MAX_COL) {
            return false;
        }
        return isHanPalace(row) || isChoPalace(row);
    }

    public static boolean canMoveDiagonally(Position from, Position to) {
        if (!isInsidePalace(from) || !isInsidePalace(to)) {
            return false;
        }
        if (!isDiagonalOneStep(from, to)) {
            return false;
        }
        return involvesCenter(from, to);
    }

    private static boolean isHanPalace(int row) {
        return row >= HAN_MIN_ROW && row <= HAN_MAX_ROW;
    }

    private static boolean isChoPalace(int row) {
        return row >= CHO_MIN_ROW && row <= CHO_MAX_ROW;
    }

    private static boolean isDiagonalOneStep(Position from, Position to) {
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());
        return rowDiff == 1 && colDiff == 1;
    }

    // 대각서 이동은 중앙을 무조건 지나야만 한다.
    private static boolean involvesCenter(Position from, Position to) {
        return from.equals(HAN_CENTER) || to.equals(HAN_CENTER)
                || from.equals(CHO_CENTER) || to.equals(CHO_CENTER);
    }

    public static boolean isDiagonalInPalace(Position from, Position to) {
        if (!isInsidePalace(from) || !isInsidePalace(to)) {
            return false;
        }
        if (!isSamePalace(from, to)) {
            return false;
        }
        return canMoveDiagonally(from, to) || isTwoStepDiagonal(from, to);
    }

    public static Position getDiagonalMidpoint(Position from, Position to) {
        if (!isTwoStepDiagonal(from, to)) {
            return null;
        }
        int midRow = (from.getRow() + to.getRow()) / 2;
        int midCol = (from.getCol() + to.getCol()) / 2;
        return new Position(midRow, midCol);
    }

    private static boolean isSamePalace(Position a, Position b) {
        return (isHanPalace(a.getRow()) && isHanPalace(b.getRow()))
                || (isChoPalace(a.getRow()) && isChoPalace(b.getRow()));
    }

    private static boolean isTwoStepDiagonal(Position from, Position to) {
        if (!isInsidePalace(from) || !isInsidePalace(to)) {
            return false;
        }
        if (!isSamePalace(from, to)) {
            return false;
        }
        int rowDiff = Math.abs(to.getRow() - from.getRow());
        int colDiff = Math.abs(to.getCol() - from.getCol());
        if (rowDiff != 2 || colDiff != 2) {
            return false;
        }

        // 2칸 대각선이면 중간 경유지가 궁성 중앙이어야 함
        int midRow = (from.getRow() + to.getRow()) / 2;
        int midCol = (from.getCol() + to.getCol()) / 2;
        Position midpoint = new Position(midRow, midCol);
        return midpoint.equals(HAN_CENTER) || midpoint.equals(CHO_CENTER);
    }


}
