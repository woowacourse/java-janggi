package janggi.board;

import janggi.position.Position;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Palace {

    private final Set<Position> area;

    public Palace() {
        this.area = new HashSet<>();
    }

    public void addArea(final Position position) {
        area.add(position);
    }

    public Set<Position> getArea() {
        return Collections.unmodifiableSet(area);
    }

    public boolean isInPalace(final Position currentPosition) {
        return area.stream()
                .anyMatch(position -> position.equals(currentPosition));
    }
}
