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
        betweenPositions.removeFirst();
        betweenPositions.removeLast();

        return betweenPositions;
    }

    public Position calculateHorseMiddlePosition(final Position destPosition) {
        final int xDistance = Math.abs(x - destPosition.x);

        int newX;
        int newY;
        if (xDistance == 1) {
            newX = x;
            newY = (y + destPosition.y) / 2;
            return new Position(newX, newY);
        }
        newX = (x + destPosition.x) / 2;
        newY = y;
        return new Position(newX, newY);
    }

    public List<Position> calculateElephantMiddlePositions(final Position destPosition) {
        final int xDistance = Math.abs(x - destPosition.x);

        if (xDistance == 3) { // x 3 y 2
            int[] newXs = new int[2];
            for (int xx = Math.min(x, destPosition.x) + 1, i = 0; xx < Math.max(x, destPosition.x); xx++, i++) {
                newXs[i] = xx;
            }
            int[] newYs = new int[2];
            if (y < destPosition.y) {
                for (int yy = y, i = 0; yy < destPosition.y; yy++, i++) {
                    newYs[i] = yy;
                }
            } else {
                for (int yy = destPosition.y - 1, i = 0; yy >= y; yy--, i++) {
                    newYs[i] = yy;
                }
            }
            return List.of(
                    new Position(newXs[0], newYs[0]),
                    new Position(newXs[1], newYs[1])
            );
        }

        int[] newYs = new int[2];
        for (int yy = Math.min(y, destPosition.y) + 1, i = 0; yy < Math.max(y, destPosition.y); yy++, i++) {
            newYs[i] = yy;
        }
        int[] newXs = new int[2];
        if (x < destPosition.x) {
            for (int xx = x, i = 0; xx < destPosition.x; xx++, i++) {
                newXs[i] = xx;
            }
        } else {
            for (int xx = destPosition.x - 1, i = 0; xx >= x; xx--, i++) {
                newXs[i] = xx;
            }
        }
        return List.of(
                new Position(newXs[0], newYs[0]),
                new Position(newXs[1], newYs[1])
        );
    }
}
