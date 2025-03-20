package domain;

import java.util.List;

public class Guard extends AbstractPiece {

    public Guard(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX == 1 && absoluteY == 0) {
            return true;
        }
        return absoluteX == 0 && absoluteY == 1;
    }
}
