package janggi.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private final List<RelativePosition> relativePositions;

    public Path(final List<RelativePosition> relativePositions){
        this.relativePositions = Collections.unmodifiableList(relativePositions);
    }

    public List<Position> calculateAbsolutePath(final Position now) {
        try {
            return makeAbsolutePath(now);
        }
        catch (final IllegalArgumentException e){
            return new ArrayList<>();
        }
    }

    private List<Position> makeAbsolutePath(final Position now) {
        final List<Position> absolutePath = new ArrayList<>();
        Position next = now;
        for (final RelativePosition relativePosition : relativePositions) {
            next = relativePosition.calculateNextPosition(next);
            absolutePath.add(next);
        }
        return absolutePath;
    }

    public boolean equalsDestination(final Position now, final Position destination) {
        try {
            final List<Position> positions = makeAbsolutePath(now);
            return destination.equals(positions.getLast());
        } catch (final IllegalArgumentException e){
            return false;
        }
    }
}
