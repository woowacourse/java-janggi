package domain;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends AbstractPiece {

    public Chariot(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> getPossiblePoint(Point prev, Point newPoint) {
        List<Point> possiblePoint = new ArrayList<>();

        int x = prev.calculateSubtractionX(newPoint);
        int y = prev.calculateSubtractionY(newPoint);
        if (x > 0) {
            Point target = prev;
            for (int i = 0; i < prev.distanceToMaxX(); i++) {
                target = target.right();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        if (x < 0) {
            Point target = prev;
            for (int i = 0; i < prev.distanceToMinX(); i++) {
                target = target.left();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        if (y > 0) {
            Point target = prev;
            for (int i = 0; i < prev.distanceToMaxY(); i++) {
                target = target.up();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        if (y < 0) {
            Point target = prev;
            for (int i = 0; i < prev.distanceToMinY(); i++) {
                target = target.down();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        return possiblePoint;
    }

    @Override
    public boolean isMovable(final Distance distance) {
        if (distance.x() == 0 && distance.y() != 0) {
            return true;
        }
        return distance.x() != 0 && distance.y() == 0;
    }
}
