package domain.point;

import domain.piece.move.Vector;

public record Point(
        int y,
        int x
) {

    public Point(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    private void validate(int y, int x) {
        if (checkPointRange(y, x)) {
            return;
        }
        throw new IllegalArgumentException("잘못된 좌표 입력입니다.");
    }

    public boolean canMake(int y, int x) {
        return checkPointRange(this.y + y, this.x + x);
    }

    private boolean checkPointRange(int y, int x) {
        return 0 <= y && y <= 9 && 0 <= x && x <= 8;
    }

    public Point movePoint(int y, int x) {
        return new Point(this.y + y, this.x + x);
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
