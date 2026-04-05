package janggi.domain.board;

import janggi.domain.board.coordinate.Point;
import java.util.Arrays;
import java.util.Optional;

public class Palace {

    private final int upper_min_x = 7;
    private final int lower_max_x = 2;
    private final int min_y = 3;
    private final int max_y = 5;

    private final int upper_center_x = 8;
    private final int lower_center_x = 1;
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
        if (point.x() >= upper_min_x && point.y() >= min_y && point.y() <= max_y) {
            return true;
        }
        return false;
    }

    public boolean isInLower(Point point) {
        if (point.x() <= lower_max_x && point.y() >= min_y && point.y() <= max_y) {
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
