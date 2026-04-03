package domain.point;

import domain.move.directions.Vector;
import domain.point.exception.PointException;

import static common.constant.JanggiConstant.*;
import static domain.point.exception.PointError.POINT_RANGE_IS_OVER;

public record Point(
        int y,
        int x
) {

    public Point {
        validatePointRange(y, x);
    }

    public Point next(Vector vector) {
        return new Point(y + vector.dy(), x + vector.dx());
    }

    public Point movePoint(int y, int x) {
        return new Point(this.y + y, this.x + x);
    }

    private void validatePointRange(int y, int x) {
        if (!checkPointRange(y, x)) {
            throw new PointException(POINT_RANGE_IS_OVER.getMessage());
        }
    }

    public boolean canMake(int y, int x) {
        return checkPointRange(this.y + y, this.x + x);
    }

    private boolean checkPointRange(int y, int x) {
        return BASE_POINT <= y && y < MAX_ROW && BASE_POINT <= x && x < MAX_FILE;
    }

}
