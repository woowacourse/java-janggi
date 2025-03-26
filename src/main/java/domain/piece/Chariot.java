package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Chariot extends AbstractPiece {

    public Chariot(final Team team) {
        super(team, Score.CHARIOT);
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
        if (y < 0) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMinY(), toPoint, Point::down);
        }
        return searchPalacePossiblePoint(fromPoint, toPoint);
    }

    private List<Point> searchPalacePossiblePoint(final Point fromPoint, final Point toPoint) {
        if (!fromPoint.isPalace()) {
            return List.of();
        }

        if (fromPoint.isGreenPalace()) {
            final Point greenPalaceCenter = Point.newInstance(4, 1);
            return addPalacePath(fromPoint, toPoint, greenPalaceCenter);
        }

        final Point redPalaceCenter = Point.newInstance(4, 8);
        return addPalacePath(fromPoint, toPoint, redPalaceCenter);
    }

    private static List<Point> addPalacePath(final Point fromPoint, final Point toPoint, final Point palaceCenter) {
        if (toPoint != palaceCenter && fromPoint != palaceCenter) {
            return List.of(palaceCenter);
        }
        return List.of();
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
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Distance distance = fromPoint.generateDistance(toPoint);
        if (distance.x() == 0 && distance.y() != 0) {
            return true;
        }
        if (distance.x() != 0 && distance.y() == 0) {
            return true;
        }
        return fromPoint.isPalace()
                && toPoint.isPalace()
                && (distance.calculateDistance() == Point.DIAGONAL_UNIT
                || distance.calculateDistance() == Point.DIAGONAL_UNIT * 2);
    }

    @Override
    public PieceType type() {
        return PieceType.CHARIOT;
    }
}
