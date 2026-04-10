package janggi.domain.board;

import java.util.Arrays;
import java.util.Optional;

import janggi.domain.board.coordinate.Point;

public class Palace {

    private static final int UPPER_MIN_X = 7;
    private static final int LOWER_MAX_X = 2;
    private static final int MIN_Y = 3;
    private static final int MAX_Y = 5;

    private static final int UPPER_CENTER_X = 8;
    private static final int LOWER_CENTER_X = 1;
    private static final int CENTER_Y = 4;

    public boolean isInPalace(Point point) {
        if (isInUpper(point) || isInLower(point)) {
            return true;
        }
        return false;
    }

    public Optional<PalacePoint> getPalacePoint(Point point) {
        int relativeY = point.y() - CENTER_Y;

        if (isInUpper(point)) {
            return findMatch(point.x() - UPPER_CENTER_X, relativeY);
        }

        if (isInLower(point)) {
            return findMatch(point.x() - LOWER_CENTER_X, relativeY);
        }

        return Optional.empty();
    }

    public boolean isInUpper(Point point) {
        if (point.x() >= UPPER_MIN_X && point.y() >= MIN_Y && point.y() <= MAX_Y) {
            return true;
        }
        return false;
    }

    public boolean isInLower(Point point) {
        if (point.x() <= LOWER_MAX_X && point.y() >= MIN_Y && point.y() <= MAX_Y) {
            return true;
        }
        return false;
    }

    private Optional<PalacePoint> findMatch(int relativeX, int relativeY) {
        return Arrays.stream(PalacePoint.values())
                .filter(p -> p.getX() == relativeX && p.getY() == relativeY)
                .findFirst();
    }
}
