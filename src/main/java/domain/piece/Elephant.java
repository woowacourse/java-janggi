package domain.piece;

import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Elephant extends AbstractPiece {

    public Elephant(final Team team) {
        super(team);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);

        if (direction.isFirstQuadrant()) {
            final boolean hasMatchPoint = QuadrantMovement.FIRST_QUADRANT.matchesExpectedPosition(fromPoint, toPoint);
            return processQuadrant(hasMatchPoint, fromPoint, Point::up, Point::right);
        }
        if (direction.isSecondQuadrant()) {
            final boolean hasMatchPoint = QuadrantMovement.SECOND_QUADRANT.matchesExpectedPosition(fromPoint, toPoint);
            return processQuadrant(hasMatchPoint, fromPoint, Point::up, Point::leftUp);
        }
        if (direction.isThirdQuadrant()) {
            final boolean hasMatchPoint = QuadrantMovement.THIRD_QUADRANT.matchesExpectedPosition(fromPoint, toPoint);
            return processQuadrant(hasMatchPoint, fromPoint, Point::left, Point::leftDown);
        }
        if (direction.isFourthQuadrant()) {
            final boolean hasMatchPoint = QuadrantMovement.FOURTH_QUADRANT.matchesExpectedPosition(fromPoint, toPoint);
            return processQuadrant(hasMatchPoint, fromPoint, Point::down, Point::rightDown);
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다.");
    }

    private List<Point> processQuadrant(
            final boolean hasMatchPoint,
            final Point fromPoint,
            final UnaryOperator<Point> firstDirection,
            final UnaryOperator<Point> secondDirection) {
        if (hasMatchPoint) {
            return searchPossiblePoint(fromPoint, firstDirection, firstDirection.andThen(secondDirection));
        }
        return searchPossiblePoint(fromPoint, secondDirection, firstDirection.andThen(secondDirection));
    }

    private enum QuadrantMovement {
        FIRST_QUADRANT(point -> point.up().rightUp().rightUp()),
        SECOND_QUADRANT(point -> point.up().leftUp().leftUp()),
        THIRD_QUADRANT(point -> point.left().leftDown().leftDown()),
        FOURTH_QUADRANT(point -> point.down().rightDown().rightDown());

        private final UnaryOperator<Point> movingOperator;

        QuadrantMovement(final UnaryOperator<Point> movingOperator) {
            this.movingOperator = movingOperator;
        }

        public boolean matchesExpectedPosition(final Point fromPoint, final Point toPoint) {
            final Point point = this.movingOperator.apply(fromPoint);
            return point.equals(toPoint);
        }
    }

    private List<Point> searchPossiblePoint(
            final Point fromPoint,
            final UnaryOperator<Point> firstMoving,
            final Function<Point, Point> secondMoving
    ) {
        final Point firstPoint = firstMoving.apply(fromPoint);
        final Point secondPoint = secondMoving.apply(firstPoint);
        return List.of(firstPoint, secondPoint);
    }

    @Override
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);
        return isLShapeMovement(direction);
    }

    private static boolean isLShapeMovement(final Direction direction) {
        return (direction.horizontalDistance() == 2 && direction.verticalDistance() == 3) ||
                (direction.horizontalDistance() == 3 && direction.verticalDistance() == 2);
    }

    @Override
    public PieceType type() {
        return PieceType.ELEPHANT;
    }
}
