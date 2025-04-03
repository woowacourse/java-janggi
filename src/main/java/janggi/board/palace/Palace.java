package janggi.board.palace;

import janggi.position.Position;
import java.util.Collections;
import java.util.Set;

public enum Palace {

    AREA(
            Set.of(
                    new Position(0, 3),
                    new Position(0, 4),
                    new Position(0, 5),
                    new Position(1, 3),
                    new Position(1, 4),
                    new Position(1, 5),
                    new Position(2, 3),
                    new Position(2, 4),
                    new Position(2, 5),

                    new Position(7, 3),
                    new Position(7, 4),
                    new Position(7, 5),
                    new Position(8, 3),
                    new Position(8, 4),
                    new Position(8, 5),
                    new Position(9, 3),
                    new Position(9, 4),
                    new Position(9, 5)
            )
    ),
    ;

    private final Set<Position> area;

    Palace(final Set<Position> area) {
        this.area = area;
    }

    public boolean isInPalace(final Position currentPosition) {
        return area.contains(currentPosition);
    }

    public boolean isInPalace(final Position currentPosition, final Position targerPosition) {
        return area.contains(currentPosition) && area.contains(targerPosition);
    }

    public Set<Position> getArea() {
        return Collections.unmodifiableSet(area);
    }
}
