package domain.piece;

import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Cannon extends AbstractPiece {

    public Cannon(final Team team) {
        super(team);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);

        if (direction.isRight()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMaxX(), toPoint, Point::right);
        }
        if (direction.isLeft()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMinX(), toPoint, Point::left);
        }
        if (direction.isUp()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMaxY(), toPoint, Point::up);
        }
        if (direction.isDown()) {
            return searchPossiblePoint(fromPoint, fromPoint.distanceToMinY(), toPoint, Point::down);
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다.");
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
        final Direction direction = fromPoint.generateDirection(toPoint);
        return isCannonMovement(direction);
    }

    private static boolean isCannonMovement(final Direction direction) {
        return isHorizontalCannonMovement(direction) || isVerticalCannonMovement(direction);
    }

    private static boolean isHorizontalCannonMovement(final Direction direction) {
        return direction.horizontalDistance() >= 2 && direction.verticalDistance() == 0;
    }

    private static boolean isVerticalCannonMovement(final Direction direction) {
        return direction.horizontalDistance() == 0 && direction.verticalDistance() >= 2;
    }

    @Override
    public PieceType type() {
        return PieceType.CANNON;
    }
}
