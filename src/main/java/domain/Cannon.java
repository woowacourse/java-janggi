package domain;

import java.util.ArrayList;
import java.util.List;

public class Cannon extends AbstractPiece {

    public Cannon(final Team team, final Score score) {
        super(team, score);
    }

    /*
        포는 말 1개를 건너 뛰어야만 이동 가능하므로 target은 prev에서 한 칸 옮긴 위치에서 시작한다.
         */
    @Override
    public List<Point> getPossiblePoint(Point prev, Point newPoint) {
        List<Point> possiblePoint = new ArrayList<>();

        int x = prev.calculateSubtractionX(newPoint);
        int y = prev.calculateSubtractionY(newPoint);
        if (x > 0) {
            Point target = prev.right();
            for (int i = 0; i < prev.distanceToMaxX(); i++) {
                target = target.right();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        if (x < 0) {
            Point target = prev.left();
            for (int i = 0; i < prev.distanceToMinX(); i++) {
                target = target.left();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        if (y > 0) {
            Point target = prev.up();
            for (int i = 0; i < prev.distanceToMaxY(); i++) {
                target = target.up();
                if (target.equals(newPoint)) {
                    break;
                }
                possiblePoint.add(target);
            }
        }
        if (y < 0) {
            Point target = prev.down();
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
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX >= 2 && absoluteY == 0) {
            return true;
        }
        return absoluteX == 0 && absoluteY >= 2;
    }
}
