package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Distance;
import domain.position.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Elephant extends AbstractPiece {

    public Elephant(final Team team, final Score score) {
        super(team, score);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final int x = fromPoint.calculateSubtractionX(toPoint);
        final int y = fromPoint.calculateSubtractionY(toPoint);
        if (x > 0 && y > 0) {
            if (QuadrantMovement.FIRST_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::up, Point::rightUp);
            }
            return searchPossiblePoint(fromPoint, Point::right, Point::rightUp);
        }
        if (x < 0 && y > 0) {
            if (QuadrantMovement.SECOND_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::up, Point::leftUp);
            }
            return searchPossiblePoint(fromPoint, Point::left, Point::leftUp);

        }
        if (x < 0 && y < 0) {
            if (QuadrantMovement.THIRD_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return searchPossiblePoint(fromPoint, Point::left, Point::leftDown);
            }
            return searchPossiblePoint(fromPoint, Point::down, Point::leftDown);
        }

        if (QuadrantMovement.THIRD_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
            return searchPossiblePoint(fromPoint, Point::down, Point::rightDown);
        }
        return searchPossiblePoint(fromPoint, Point::right, Point::rightDown);
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
    public boolean isMovable(final Distance distance) {
        final int absoluteX = Math.abs(distance.x());
        final int absoluteY = Math.abs(distance.y());
        if (absoluteX == 2 && absoluteY == 3) {
            return true;
        }
        return absoluteX == 3 && absoluteY == 2;
    }

    @Override
    public PieceType type() {
        return PieceType.ELEPHANT;
    }
}
