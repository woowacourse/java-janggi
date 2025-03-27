package janggi.board.point;

public record Point(
        int x,
        int y
) {

    public boolean isHorizontal(Point otherPoint) {
        return this.y == otherPoint.y;
    }

    public boolean isVertical(Point otherPoint) {
        return this.x == otherPoint.x;
    }

    public int calculateXDistance(Point otherPoint) {
        return Math.abs(this.x - otherPoint.x);
    }

    public int calculateYDistance(Point otherPoint) {
        return Math.abs(this.y - otherPoint.y);
    }

    public boolean isBehind(Point other) {
        return this.y < other.y;
    }

    public boolean isOneStepAway(Point other) {
        return manhattanDistance(other) == 1;
    }

    private int manhattanDistance(Point other) {
        return calculateXDistance(other) + calculateYDistance(other);
    }

    public Point getNextHorizontalStep(Point other) {
        if (this.x < other.x) {
            return new Point(this.x + 1, this.y);
        }
        return new Point(this.x - 1, this.y);
    }

    public Point getNextVerticalStep(Point other) {
        if (this.y < other.y) {
            return new Point(this.x, this.y + 1);
        }
        return new Point(this.x, this.y - 1);
    }

    public Point getNextDiagonalStep(Point other) {
        if (!isDiagonal(other)) {
            throw new IllegalArgumentException("대각선 이동이 아닌 경우 다음 위치를 계산할 수 없습니다.");
        }
        int xStep = Integer.signum(other.x - this.x);
        int yStep = Integer.signum(other.y - this.y);
        return new Point(this.x + xStep, this.y + yStep);
    }

    public Point middlePoint(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }

    public boolean isDiagonal(Point other) {
        return Math.abs(this.x - other.x) == Math.abs(this.y - other.y);
    }

    public boolean isOneDiagonalStepAway(Point other) {
        return isDiagonal(other) && manhattanDistance(other) == 2;
    }
}
