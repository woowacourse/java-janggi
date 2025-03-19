package board;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record Position(int x, int y) {

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
        betweenPositions.removeLast();

        return betweenPositions;
    }
}
