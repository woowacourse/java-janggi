package janggi.coordinate;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Position(int x, int y) {

    public static final int POSITION_RANGE_X_MIN = 1;
    public static final int POSITION_RANGE_X_MAX = 10;
    public static final int POSITION_RANGE_Y_MIN = 1;
    public static final int POSITION_RANGE_Y_MAX = 9;

    public Position(final int x, final int y) {
        validatePositionRange(x, y);
        this.x = x;
        this.y = y;
    }

    private void validatePositionRange(final int x, final int y) {
        if (x < POSITION_RANGE_X_MIN || POSITION_RANGE_X_MAX < x) {
            throw new IllegalArgumentException("좌표 범위가 벗어났습니다.");
        }
        if (y < POSITION_RANGE_Y_MIN || POSITION_RANGE_Y_MAX < y) {
            throw new IllegalArgumentException("좌표 범위가 벗어났습니다.");
        }
    }

    public double calculateDistance(final Position descPosition) {
        return Math.sqrt(
                Math.pow(Math.abs(this.x - descPosition.x), 2) + Math.pow(Math.abs(this.y - descPosition.y), 2));
    }

    public boolean isSameLine(final Position descPosition) {
        return x == descPosition.x || y == descPosition.y;
    }

    public boolean isXGreaterThan(final Position descPosition) {
        return x >= descPosition.x;
    }

    public boolean isXLessThan(final Position descPosition) {
        return x <= descPosition.x;
    }

    public Position plusPosition(final int x, final int y){
        return new Position(this.x + x, this.y + y);
    }

    public List<Position> calculateBetweenPositions(final Position destPosition) {
        if (!isSameLine(destPosition)) {
            return Collections.emptyList();
        }
        final List<Position> betweenPositions = new ArrayList<>();
        final int minX = Math.min(x, destPosition.x);
        final int minY = Math.min(y, destPosition.y);
        final int maxX = Math.max(x, destPosition.x);
        final int maxY = Math.max(y, destPosition.y);

        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                betweenPositions.add(new Position(i, j));
            }
        }
        betweenPositions.removeFirst();
        betweenPositions.removeLast();

        return betweenPositions;
    }

}
