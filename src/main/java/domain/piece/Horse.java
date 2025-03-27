package domain.piece;

import domain.Score;
import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.List;
import java.util.function.UnaryOperator;

public class Horse extends AbstractPiece {

    public Horse(final Team team) {
        super(team, Score.HORSE);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        final int x = fromPoint.calculateSubtractionX(toPoint);
        final int y = fromPoint.calculateSubtractionY(toPoint);
        if (isFirstQuadrant(x, y)) {
            if (QuadrantMovement.FIRST_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.up());
            }
            return List.of(fromPoint.right());
        }
        if (isSecondQuadrant(x, y)) {
            if (QuadrantMovement.SECOND_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.up());
            }
            return List.of(fromPoint.left());
        }
        if (isThirdQuadrant(x, y)) {
            if (QuadrantMovement.THIRD_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
                return List.of(fromPoint.left());
            }
            return List.of(fromPoint.down());
        }
        if (QuadrantMovement.FOURTH_QUADRANT.matchesExpectedPosition(fromPoint, toPoint)) {
            return List.of(fromPoint.down());
        }
        return List.of(fromPoint.right());
    }

    private boolean isFirstQuadrant(final int x, final int y) {
        return x > 0 && y > 0;
    }

    private boolean isSecondQuadrant(final int x, final int y) {
        return x < 0 && y > 0;
    }

    private boolean isThirdQuadrant(final int x, final int y) {
        return x < 0 && y < 0;
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
        final int absoluteX = Math.abs(direction.x());
        final int absoluteY = Math.abs(direction.y());
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
