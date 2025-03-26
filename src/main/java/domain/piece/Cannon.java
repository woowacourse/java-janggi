package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Cannon extends AbstractPiece {

    public Cannon(final Team team) {
        super(team, Score.CANNON);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final int x = fromPoint.calculateSubtractionX(toPoint);
        final int y = fromPoint.calculateSubtractionY(toPoint);
        if (x > 0) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMaxX(), toPoint, Point::right);
        }
        if (x < 0) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMinX(), toPoint, Point::left);
        }
        if (y > 0) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMaxY(), toPoint, Point::up);
        }
        return searchPossiblePoint(fromPoint, fromPoint.distanceToMinY(), toPoint, Point::down);
    }

    private List<Point> searchPossiblePoint(
            final Point fromPoint,
            final int bordEdge,
            final Point toPoint,
            final UnaryOperator<Point> directionOperator

    ) {
        final List<Point> possiblePoint = new ArrayList<>();
        Point target = fromPoint;
        for (int i = 0; i < bordEdge; i++) {
            target = directionOperator.apply(target);
            if (target.equals(toPoint)) {
                break;
            }
            possiblePoint.add(target);
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

    @Override
    public PieceType type() {
        return PieceType.CANNON;
    }
}
