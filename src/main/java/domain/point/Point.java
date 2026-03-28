package domain.point;

import domain.piece.move.Vector;

public record Point(
        int y,
        int x
) {

    private static final int BASE_POINT = 0;
    private static final int LENGTH_OF_ROW = 10;
    private static final int LENGTH_OF_FILE = 9;
    private static final int MAX_OF_ROW = BASE_POINT + LENGTH_OF_ROW;
    private static final int MAX_OF_FILE = BASE_POINT + LENGTH_OF_FILE;

    public Point {
        validatePointRange(y, x);
    }

    private void validatePointRange(int y, int x) {
        if (checkPointRange(y, x)) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public boolean canMake(int y, int x){
        return checkPointRange(this.y + y, this.x + x);
    }

    public Point movePoint(int y, int x) {
        return new Point(this.y + y, this.x + x);
    }

    private boolean checkPointRange(int y, int x) {
        return BASE_POINT <= y && y < MAX_OF_ROW && BASE_POINT <= x && x < MAX_OF_FILE;
    }

    public boolean isSameFile(Point other) {
        return this.y == other.y;
    }

    public boolean isSameRow(Point other) {
        return this.x == other.x;
    }

    public Point next(Vector vector) {
        return new Point(y + vector.dy(), x + vector.dx());
    }

}
