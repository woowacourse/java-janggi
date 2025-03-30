package domain.board;

import domain.movements.Direction;
import dto.Choice;
import java.util.List;

public record Point(int row, int column) {

    public Point(List<Choice> request) {
        this(request.get(0).value(), request.get(1).value());
    }

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }

    public static Point generateStartPoint(final List<List<Choice>> moveRequest) {
        final List<Choice> originPointRequest = moveRequest.get(0);
        return new Point(originPointRequest);
    }

    public static Point generateArrivalPoint(final List<List<Choice>> moveRequest) {
        final List<Choice> arrivalPointRequest = moveRequest.get(1);
        return new Point(arrivalPointRequest);
    }

    public Point move(final Direction direction) {
        return new Point(row + direction.getRow(), column + direction.getColumn());
    }

    public boolean isInRange(int maxRow, int maxColumn) {
        return isInRangeOnRow(maxRow) && isInRangeOnColumn(maxColumn);
    }

    public boolean isInSquareRange(Point squareStartPoint, Point squareEndPoint) {
        return isInSquareRangeOnRow(squareStartPoint, squareEndPoint)
                && isInSquareRangeOnColumn(squareStartPoint, squareEndPoint);
    }

    private boolean isInRangeOnRow(int maxRow) {
        return row >= 0 && row < maxRow;
    }

    private boolean isInRangeOnColumn(int maxColumn) {
        return column >= 0 && column < maxColumn;
    }

    private boolean isInSquareRangeOnColumn(Point squareStartPoint, Point squareEndPoint) {
        return this.column() >= squareStartPoint.column() && this.column() <= squareEndPoint.column();
    }

    private boolean isInSquareRangeOnRow(Point squareStartPoint, Point squareEndPoint) {
        return this.row() >= squareStartPoint.row() && this.row() <= squareEndPoint.row();
    }
}
