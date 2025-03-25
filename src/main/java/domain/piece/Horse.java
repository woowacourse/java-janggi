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
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final List<Point> possiblePoint = new ArrayList<>();

        final int x = fromPoint.calculateSubtractionX(toPoint);
        final int y = fromPoint.calculateSubtractionY(toPoint);
        if (x > 0 && y > 0) {
            final Point point = fromPoint.up().rightUp();

            if (point.equals(toPoint)) {
                possiblePoint.add(fromPoint.up());
            } else {
                possiblePoint.add(fromPoint.right());
            }
        }
        if (x < 0 && y > 0) {
            final Point point = fromPoint.up().leftUp();
            if (point.equals(toPoint)) {
                possiblePoint.add(fromPoint.up());
            } else {
                possiblePoint.add(fromPoint.left());
            }
        }
        if (x < 0 && y < 0) {
            final Point point = fromPoint.left().leftDown();
            if (point.equals(toPoint)) {
                possiblePoint.add(fromPoint.left());
            } else {
                possiblePoint.add(fromPoint.down());
            }
        }
        if (x > 0 && y < 0) {
            final Point point = fromPoint.down().rightDown();
            if (point.equals(toPoint)) {
                possiblePoint.add(fromPoint.down());
            } else {
                possiblePoint.add(fromPoint.right());
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

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
