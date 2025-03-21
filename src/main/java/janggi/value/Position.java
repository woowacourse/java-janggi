package janggi.value;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public final class Position implements Comparable<Position> {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static List<Position> makePositionInXLine(int startX, int endX, int y) {
        return IntStream.rangeClosed(startX, endX)
                .mapToObj(x -> new Position(x, y))
                .toList();
    }

    public static List<Position> makePositionInYLine(int startY, int endY, int x) {
        return IntStream.rangeClosed(startY, endY)
                .mapToObj(y -> new Position(x, y))
                .toList();
    }

    public boolean isEqualsXPosition(int otherX) {
        return x == otherX;
    }

    public boolean isEqualsYPosition(int otherY) {
        return y == otherY;
    }

    @Override
    public int compareTo(Position otherPosition) {
        if (y == otherPosition.getY()) {
            return Integer.compare(otherPosition.getX(), x);
        }
        return Integer.compare(otherPosition.getY(), y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
