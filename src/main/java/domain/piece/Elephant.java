package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends AbstractPiece {

    public Elephant(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> getPossiblePoint(final Point prev, final Point newPoint) {
        final List<Point> possiblePoint = new ArrayList<>();

        final int x = prev.calculateSubtractionX(newPoint);
        final int y = prev.calculateSubtractionY(newPoint);
        if (x > 0 && y > 0) {
            final Point point = prev.up().rightUp().rightUp();

            if (point.equals(newPoint)) {
                possiblePoint.add(prev.up());
                possiblePoint.add(prev.up().rightUp());
            } else {
                possiblePoint.add(prev.right());
                possiblePoint.add(prev.right().rightUp());
            }
        }
        if (x < 0 && y > 0) {
            final Point point = prev.up().leftUp().leftUp();
            if (point.equals(newPoint)) {
                possiblePoint.add(prev.up());
                possiblePoint.add(prev.up().leftUp());
            } else {
                possiblePoint.add(prev.left());
                possiblePoint.add(prev.left().leftUp());
            }

        }
        if (x < 0 && y < 0) {
            final Point point = prev.left().leftDown().leftDown();
            if (point.equals(newPoint)) {
                possiblePoint.add(prev.left());
                possiblePoint.add(prev.left().leftDown());
            } else {
                possiblePoint.add(prev.down());
                possiblePoint.add(prev.down().leftDown());
            }

        }
        if (x > 0 && y < 0) {
            final Point point = prev.down().rightDown().rightDown();
            if (point.equals(newPoint)) {
                possiblePoint.add(prev.down());
                possiblePoint.add(prev.down().rightDown());
            } else {
                possiblePoint.add(prev.right());
                possiblePoint.add(prev.right().rightDown());
            }
        }
        return possiblePoint;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX == 2 && absoluteY == 3) {
            return true;
        }
        return absoluteX == 3 && absoluteY == 2;
    }
}
