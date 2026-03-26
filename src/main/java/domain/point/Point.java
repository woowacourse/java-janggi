package domain.point;

import domain.piece.move.Direction;

import java.util.List;

public record Point(
        int y,
        int x
) {

    public Point(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    public Point movePoint(int y, int x) {
        return new Point(this.y + y, this.x + x);
    }

    private void validate(int y, int x) {
        if (checkPointRange(y, x)) {
            return;
        }
        throw new IllegalArgumentException();
    }

    private boolean checkPointRange(int y, int x) {
        return 0 <= y && y <= 9 && 0 <= x && x <= 8;
    }

    public boolean isSameFile(Point other) {
        return this.y == other.y;
    }

    public boolean isSameRow(Point other) {
        return this.x == other.x;
    }

    public Point next(Direction direction) {
        return new Point(y + direction.dy(), x + direction.dx());
    }
}
