package domain.point;

import domain.move.Vector;
import java.util.List;

public record Point(
        int y,
        int x
) {
    private static final List<Integer> PALACE_ROWS = List.of(0, 1, 2, 7, 8, 9);
    private static final List<Integer> PALACE_FILES = List.of(3, 4, 5);
    private static final List<Integer> CORNER_ROWS = List.of(0, 2, 7, 9);
    private static final List<Integer> CENTER_ROWS = List.of(1, 8);
    private static final List<Integer> CORNER_FILES = List.of(3, 5);
    private static final int CENTER_FILE = 4;

    public Point(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    private static void validate(int y, int x) {
        if (checkPointRange(y, x)) {
            return;
        }
        throw new exception.InvalidPointException();
    }

    private static boolean checkPointRange(int y, int x) {
        return 0 <= y && y <= 9 && 0 <= x && x <= 8;
    }

    public boolean isPalace() {
        return PALACE_ROWS.contains(this.y) && PALACE_FILES.contains(this.x);
    }

    public boolean isPalaceDiagonal() {
        return (CORNER_ROWS.contains(this.y) && CORNER_FILES.contains(this.x))
                || (CENTER_ROWS.contains(this.y) && this.x == CENTER_FILE);
    }

    public boolean canMake(int y, int x) {
        return checkPointRange(this.y + y, this.x + x);
    }

    public Point movePoint(int y, int x) {
        return new Point(this.y + y, this.x + x);
    }

    public boolean isSameFile(Point other) {
        return this.x == other.x;
    }

    public boolean isSameRow(Point other) {
        return this.y == other.y;
    }

    public Point next(Vector vector) {
        return new Point(y + vector.dy(), x + vector.dx());
    }
}
