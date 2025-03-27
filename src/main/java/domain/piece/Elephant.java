package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Elephant extends AbstractPiece {

    public Elephant(final Team team) {
        super(team, Score.ELEPHANT);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);

        if (direction.isFirstQuadrant()) {
            if (QuadrantMovement.FIRST_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::up, Point::rightUp);
            }
            return searchPossiblePoint(fromPoint, Point::right, Point::rightUp);
        }
        if (direction.isSecondQuadrant()) {
            if (QuadrantMovement.SECOND_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::up, Point::leftUp);
            }
            return searchPossiblePoint(fromPoint, Point::left, Point::leftUp);

        }
        if (direction.isThirdQuadrant()) {
            if (QuadrantMovement.THIRD_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::left, Point::leftDown);
            }
            return searchPossiblePoint(fromPoint, Point::down, Point::leftDown);
        }
        if (direction.isFourthQuadrant()) {
            if (QuadrantMovement.FOURTH_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::down, Point::rightDown);
            }
            return searchPossiblePoint(fromPoint, Point::right, Point::rightDown);
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다.");
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
            final UnaryOperator<Point> secondMoving
    ) {
        final List<Point> possiblePoint = new ArrayList<>();
        final Point apply = firstMoving.apply(fromPoint);
        possiblePoint.add(apply);
        possiblePoint.add(secondMoving.apply(apply));
        return possiblePoint;
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
