package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;

public class Horse extends AbstractPiece {

    public Horse(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
        final List<Point> possiblePoint = new ArrayList<>();

        final int x = prev.calculateSubtractionX(newPoint);
        final int y = prev.calculateSubtractionY(newPoint);
        if (x > 0 && y > 0) {
            final Point point = prev.up().rightUp();

            if (point.equals(newPoint)) {
                possiblePoint.add(prev.up());
            } else {
                possiblePoint.add(prev.right());
            }
        }
        if (x < 0 && y > 0) {
            final Point point = prev.up().leftUp();
            if (point.equals(newPoint)) {
                possiblePoint.add(prev.up());
            } else {
                possiblePoint.add(prev.left());
            }
        }
        if (x < 0 && y < 0) {
            final Point point = prev.left().leftDown();
            if (point.equals(newPoint)) {
                possiblePoint.add(prev.left());
            } else {
                possiblePoint.add(prev.down());
            }
        }
        if (x > 0 && y < 0) {
            final Point point = prev.down().rightDown();
            if (point.equals(newPoint)) {
                possiblePoint.add(prev.down());
            } else {
                possiblePoint.add(prev.right());
            }
        }
        return possiblePoint;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX == 1 && absoluteY == 2) {
            return true;
        }
        return absoluteX == 2 && absoluteY == 1;
    }
}
