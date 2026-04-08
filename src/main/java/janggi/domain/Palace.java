package janggi.domain;

import java.util.Set;

public class Palace {

    private static final Set<Position> BOUNDARY = PalaceFactory.generateBoundary();
    private static final Set<Position> CENTER = PalaceFactory.generateCenter();

    private Palace() {
    }

    public static boolean onPalace(Position position) {
        return BOUNDARY.contains(position);
    }

    public static boolean isPalaceCenter(Position position) {
        return CENTER.contains(position);
    }
}
