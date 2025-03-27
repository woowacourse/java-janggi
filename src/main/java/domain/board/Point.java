package domain.board;

import domain.movements.Direction;
import java.util.List;

public record Point(int row, int column) {

    public Point(List<Integer> request) {
        this(request.get(0), request.get(1));
    }

    public static Point generateStartPoint(final List<List<Integer>> moveRequest) {
        final List<Integer> originPointRequest = moveRequest.get(0);
        return new Point(originPointRequest);
    }

    public static Point generateArrivalPoint(final List<List<Integer>> moveRequest) {
        final List<Integer> arrivalPointRequest = moveRequest.get(1);
        return new Point(arrivalPointRequest);
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    public Point move(final Direction direction) {
        return new Point(row + direction.getRow(), column + direction.getColumn());
    }

    public boolean isInRange(int maxRow, int maxColumn) {
        return isInRangeOnRow(maxRow) && isInRangeOnColumn(maxColumn);
    }

    private boolean isInRangeOnRow(int maxRow) {
        return row >= 0 && row < maxRow;
    }

    private boolean isInRangeOnColumn(int maxColumn) {
        return column >= 0 && column < maxColumn;
    }
}
