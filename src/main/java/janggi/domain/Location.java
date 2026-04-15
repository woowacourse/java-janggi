package janggi.domain;

import java.util.List;

public record Location(int row, int col) {

    private static final int COORDINATION_COUNT = 2;

    public static Location from(List<Integer> coordination) {
        validateCount(coordination);
        int row = coordination.get(0) - 1;
        int col = coordination.get(1) - 1;
        return new Location(row, col);
    }

    private static void validateCount(List<Integer> coordination) {
        if (coordination.size() != COORDINATION_COUNT) {
            throw new IllegalArgumentException("좌표의 개수는 " + COORDINATION_COUNT + "개 입니다.");
        }
    }

    public Location add(int rowDiff, int colDiff) {
        return new Location(row + rowDiff, col + colDiff);
    }

    public int calculateHorizontalDiff(Location to) {
        return to.col - this.col;
    }

    public int calculateVerticalDiff(Location to) {
        return to.row - this.row;
    }

    public String getFormattedLocation() {
        return "(" + (row + 1) + ", " + (col + 1) + ")";
    }
}
