package janggi.value;

import java.util.List;
import java.util.stream.IntStream;

public record Position(int x, int y) {

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

    public Position calculateDifference(Position other) {
        int xDifference = x - other.x();
        int yDifference = y - other.y();
        return new Position(xDifference, yDifference);
    }

    public Position calculateSum(Position other) {
        int xSum = x + other.x();
        int ySum = y + other.y();
        return new Position(xSum, ySum);
    }

    public boolean isEqualsXPosition(int otherX) {
        return x == otherX;
    }

    public boolean isEqualsYPosition(int otherY) {
        return y == otherY;
    }
}
