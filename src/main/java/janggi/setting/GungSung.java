package janggi.setting;

import janggi.value.Position;
import java.util.List;

public enum GungSung {
    TOP_GUNGSUNG(3, 5, 0, 2),
    BOTTOM_GUNGSUNG(3, 5, 7, 9);
    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    GungSung(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public static boolean isInAnyGungSung(Position position) {
        List<GungSung> gungSungs = List.of(GungSung.values());
        return gungSungs.stream().anyMatch(gungSung -> gungSung.isInGungSung(position));
    }

    private boolean isInGungSung(Position position) {
        boolean isXInOfRange = position.x() >= minX && position.x() <= maxX;
        boolean isYInOfRange = position.y() >= minY && position.y() <= maxY;
        return isXInOfRange && isYInOfRange;
    }
}
