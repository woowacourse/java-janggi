package domain.piece;

import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.List;
import java.util.function.UnaryOperator;

public class Horse extends AbstractPiece {

    public Horse(final Team team) {
        super(team);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);

        if (direction.isFirstQuadrant()) {
            if (QuadrantMovement.FIRST_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.up());
            }
            return List.of(fromPoint.right());
        }
        if (direction.isSecondQuadrant()) {
            if (QuadrantMovement.SECOND_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.up());
            }
            return List.of(fromPoint.left());
        }
        if (direction.isThirdQuadrant()) {
            if (QuadrantMovement.THIRD_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.left());
            }
            return List.of(fromPoint.down());
        }
        if (direction.isFourthQuadrant()) {
            if (QuadrantMovement.FOURTH_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.down());
            }
            return List.of(fromPoint.right());
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다.");
    }

    private enum QuadrantMovement {
        FIRST_QUADRANT(point -> point.up().rightUp()),
        SECOND_QUADRANT(point -> point.up().leftUp()),
        THIRD_QUADRANT(point -> point.left().leftDown()),
        FOURTH_QUADRANT(point -> point.down().rightDown());

        private final UnaryOperator<Point> movingOperator;

        QuadrantMovement(final UnaryOperator<Point> movingOperator) {
            this.movingOperator = movingOperator;
        }

        public boolean matchesExpectedPosition(final Point fromPoint, final Point toPoint) {
            final Point point = this.movingOperator.apply(fromPoint);
            return point.equals(toPoint);
        }
    }

    @Override
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);
        return isLShapeMovement(direction);
    }

    private static boolean isLShapeMovement(final Direction direction) {
        return (direction.horizontalDistance() == 1 && direction.verticalDistance() == 2) ||
                (direction.horizontalDistance() == 2 && direction.verticalDistance() == 1);
    }

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
