package domain;

import java.util.List;

public class Chariot extends AbstractPiece {

    public Chariot(final Team team) {
        super(team);
    }

    @Override
    public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
        return List.of();
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (distance.x() == 0 && distance.y() != 0) {
            return true;
        }
        return distance.x() != 0 && distance.y() == 0;
    }
}
