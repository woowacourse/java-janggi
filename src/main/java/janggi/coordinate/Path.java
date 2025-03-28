package janggi.coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private final List<RelativePosition> relativePositions;

    public Path(final List<RelativePosition> relativePositions){
        this.relativePositions = Collections.unmodifiableList(relativePositions);
    }

    public List<JanggiPosition> calculateAbsolutePath(final JanggiPosition now) {
        try {
            return makeAbsolutePath(now);
        }
        catch (final IllegalArgumentException e){
            return new ArrayList<>();
        }
    }

    private List<JanggiPosition> makeAbsolutePath(final JanggiPosition now) {
        final List<JanggiPosition> absolutePath = new ArrayList<>();
        JanggiPosition next = now;
        for (final RelativePosition relativePosition : relativePositions) {
            next = relativePosition.calculateNextPosition(next);
            absolutePath.add(next);
        }
        return absolutePath;
    }

    public boolean equalsDestination(final JanggiPosition now, final JanggiPosition destination) {
        try {
            final List<JanggiPosition> janggiPositions = makeAbsolutePath(now);
            return destination.equals(janggiPositions.getLast());
        } catch (final IllegalArgumentException e){
            return false;
        }
    }
}
