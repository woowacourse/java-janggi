package janggi.domain.board;

import janggi.domain.board.coordinate.Point;
import java.util.Arrays;
import java.util.Optional;

public class Palace {
    private final int lower_center_x = 1;
    private final int upper_center_x = 8;
    private final int center_y = 4;

    public boolean isInPalace(Point point) {
        if (isInUpper(point) || isInLower(point)) {
            return true;
        }
        return false;
    }

    public Optional<PalacePoint> getPalacePoint(Point point) {
        int relativeY = point.y() - center_y;

        if (isInUpper(point)) {
            return findMatch(point.x() - upper_center_x, relativeY);
        }

        if (isInLower(point)) {
            return findMatch(point.x() - lower_center_x, relativeY);
        }

        return Optional.empty();
    }

    public boolean isInUpper(Point point) {
        if (point.x() >= 7 && point.y() >= 3 && point.y() <= 5) {
            return true;
        }
        return false;
    }

    public boolean isInLower(Point point) {
        if (point.x() <= 2 && point.y() >= 3 && point.y() <= 5) {
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
