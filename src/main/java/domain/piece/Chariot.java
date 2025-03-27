package domain.piece;

import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Chariot extends AbstractPiece {

    public Chariot(final Team team) {
        super(team);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);

        if (isDiagonalDirectionInPalace(fromPoint, toPoint, direction)) {
            return searchPalacePossiblePoint(fromPoint, toPoint);
        }
        if (direction.isRight()) {
            return searchPossiblePoint(fromPoint, direction.horizontalDistance(), Point::right);
        }
        if (direction.isLeft()) {
            return searchPossiblePoint(fromPoint, direction.horizontalDistance(), Point::left);
        }
        if (direction.isUp()) {
            return searchPossiblePoint(fromPoint, direction.verticalDistance(), Point::up);
        }
        if (direction.isDown()) {
            return searchPossiblePoint(fromPoint, direction.verticalDistance(), Point::down);
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다.");
    }

    private static boolean isDiagonalDirectionInPalace(
            final Point fromPoint,
            final Point toPoint,
            final Direction direction
    ) {
        return fromPoint.isPalace() && toPoint.isPalace() && isDiagonalDirection(direction);
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
            final int count,
            final UnaryOperator<Point> directionOperator
    ) {
        final List<Point> possiblePoint = new ArrayList<>();
        Point target = fromPoint;
        for (int i = 1; i < count; i++) {
            target = directionOperator.apply(target);
            possiblePoint.add(target);
        }
        return possiblePoint;
    }

    @Override
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);
        return isVerticalDirection(direction) ||
                isHorizontalDirection(direction) ||
                isDiagonalDirectionInPalace(fromPoint, toPoint, direction);
    }

    private static boolean isDiagonalDirection(final Direction direction) {
        return direction.calculateDistance() == Point.DIAGONAL_UNIT
                || direction.calculateDistance() == Point.DIAGONAL_UNIT * 2;
    }

    private static boolean isHorizontalDirection(final Direction direction) {
        return !direction.isNotHorizontal() && direction.isNotVertical();
    }

    private static boolean isVerticalDirection(final Direction direction) {
        return direction.isNotHorizontal() && !direction.isNotVertical();
    }

    @Override
    public PieceType type() {
        return PieceType.CHARIOT;
    }
}
