package domain.piece;

import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.List;

public class General extends AbstractPiece {

    public General(final Team team) {
        super(team);
    }

    @Override
    public List<Point> calculatePossiblePoint(final Point fromPoint, final Point toPoint) {
        if (isMovable(fromPoint, toPoint)) {
            return List.of();
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다.");
    }

    @Override
    public boolean isMovable(final Point fromPoint, final Point toPoint) {
        final Direction direction = fromPoint.generateDirection(toPoint);
        if (!isInPalace(toPoint)) {
            return false;
        }

        return isHorizontalMovement(direction)
                || isVerticalMovement(direction)
                || (isInDiagonalPalace(toPoint) && isDiagonalDirection(direction));
    }

    private boolean isInPalace(final Point toPoint) {
        return toPoint.isPalace();
    }

    private boolean isInDiagonalPalace(final Point toPoint) {
        return toPoint.isDiagonalPalace();
    }

    private boolean isHorizontalMovement(final Direction direction) {
        return direction.horizontalDistance() == 1 && direction.verticalDistance() == 0;
    }

    private boolean isVerticalMovement(final Direction direction) {
        return direction.horizontalDistance() == 0 && direction.verticalDistance() == 1;
    }

    private boolean isDiagonalDirection(final Direction direction) {
        return direction.calculateDistance() == Point.DIAGONAL_UNIT;
    }

    @Override
    public PieceType type() {
        return PieceType.GENERAL;
    }
}
