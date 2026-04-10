package janggi.domain.board;

import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        validatePositionRange(x, y);
        this.x = x;
        this.y = y;
    }

    public int calculateX(Position other) {
        return other.x - this.x;
    }

    public int calculateY(Position other) {
        return other.y - this.y;
    }

    public int calculateManhattanDistance(Position other) {
        return Math.abs(calculateX(other)) + Math.abs(calculateY(other));
    }

    public boolean isInSameLine(Position other) {
        if (other.equals(this)) {
            return false;
        }
        return other.x == this.x || other.y == this.y;
    }

    public boolean isOneSameDiagonal(Position other) {
        if (other.equals(this)) {
            return false;
        }
        return Math.abs(other.x - this.x) == Math.abs(other.y - this.y);
    }

    public boolean isMatchDistance(Position other, int value1, int value2) {
        int xDistance = Math.abs(calculateX(other));
        int yDistance = Math.abs(calculateY(other));
        return (xDistance == value1 && yDistance == value2) || (xDistance == value2 && yDistance == value1);
    }

    public Position moveStraight(Position to) {
        int xDistance = calculateX(to);
        int yDistance = calculateY(to);
        if (Math.abs(xDistance) > Math.abs(yDistance)) {
            return new Position(this.x + Integer.signum(xDistance), this.y);
        }
        return new Position(this.x, this.y + Integer.signum(yDistance));
    }

    public Position moveDiagonal(Position to) {
        int xDistance = calculateX(to);
        int yDistance = calculateY(to);
        return new Position(this.x + Integer.signum(xDistance), this.y + Integer.signum(yDistance));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void validatePositionRange(int x, int y) {
        validateXRange(x);
        validateYRange(y);
    }

    private void validateXRange(int x) {
        if (1 > x || x > 9) {
            throw new IllegalArgumentException("X 좌표의 범위는 1~9 사이여야 합니다.");
        }
    }

    private void validateYRange(int y) {
        if (1 > y || y > 10) {
            throw new IllegalArgumentException("Y 좌표의 범위는 1~10 사이여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean isInRange(int minX, int maxX, int minY, int maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
