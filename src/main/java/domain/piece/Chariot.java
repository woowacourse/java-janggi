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
        final Distance distance = fromPoint.generateDistance(toPoint);

        if (isDiagonalDirectionInPalace(fromPoint, toPoint, distance)) {
            return searchPalacePossiblePoint(fromPoint, toPoint);
        }
        if (distance.isRight()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMaxX(), toPoint, Point::right);
        }
        if (distance.isLeft()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMinX(), toPoint, Point::left);
        }
        if (distance.isUp()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMaxY(), toPoint, Point::up);
        }
        if (distance.isDown()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMinY(), toPoint, Point::down);
        }
        throw new IllegalArgumentException("차는 해당 방향으로 움직일 수 없습니다.");
    }

    private static boolean isDiagonalDirectionInPalace(
            final Point fromPoint,
            final Point toPoint,
            final Distance distance
    ) {
        return fromPoint.isPalace() && toPoint.isPalace() && isDiagonalDirection(distance);
    }

    private List<Point> searchPalacePossiblePoint(final Point fromPoint, final Point toPoint) {
        if (fromPoint.isGreenPalace()) {
            final Point greenPalaceCenter = Point.newInstance(4, 1);
            return addPalacePath(fromPoint, toPoint, greenPalaceCenter);
        }

        final Point redPalaceCenter = Point.newInstance(4, 8);
        return addPalacePath(fromPoint, toPoint, redPalaceCenter);
    }

    private static List<Point> addPalacePath(final Point fromPoint, final Point toPoint, final Point palaceCenter) {
        if (isNotPalaceCenterBoth(fromPoint, toPoint, palaceCenter)) {
            return List.of(palaceCenter);
        }
        return List.of();
    }

    private static boolean isNotPalaceCenterBoth(final Point fromPoint, final Point toPoint, final Point palaceCenter) {
        return !(palaceCenter.equals(fromPoint) || palaceCenter.equals(toPoint));
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
        if (isVerticalDirection(distance)) {
            return true;
        }
        if (isHorizontalDirection(distance)) {
            return true;
        }
        return isDiagonalDirectionInPalace(fromPoint, toPoint, distance);
    }

    private static boolean isDiagonalDirection(final Distance distance) {
        return distance.calculateDistance() == Point.DIAGONAL_UNIT
                || distance.calculateDistance() == Point.DIAGONAL_UNIT * 2;
    }

    private static boolean isHorizontalDirection(final Distance distance) {
        return !distance.isNotHorizontal() && distance.isNotVertical();
    }

    private static boolean isVerticalDirection(final Distance distance) {
        return distance.isNotHorizontal() && !distance.isNotVertical();
    }

    @Override
    public PieceType type() {
        return PieceType.CHARIOT;
    }
}
