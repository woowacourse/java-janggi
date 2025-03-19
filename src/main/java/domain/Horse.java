package domain;

import java.util.List;

public class Horse extends AbstractPiece {

    public Horse(final Team team) {
        super(team);
    }

    @Override
    public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Distance distance) {
        int absoluteX = Math.abs(distance.x());
        int absoluteY = Math.abs(distance.y());
        if (absoluteX == 1 && absoluteY == 2) {
            return true;
        }
        return absoluteX == 2 && absoluteY == 1;
    }
}
