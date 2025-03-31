package domain.piece;

import domain.Team;
import domain.position.Direction;
import domain.position.Point;
import java.util.List;

public class Soldier extends AbstractPiece {

    public Soldier(final Team team) {
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
        if (isGreenTeam()) {
            return isGreenMovement(direction, fromPoint, toPoint);
        }
        return isRedMovement(direction, fromPoint, toPoint);
    }

    private boolean isGreenMovement(final Direction direction, final Point fromPoint, final Point toPoint) {
        if (isUpMovement(direction) || isHorizontalMovement(direction)) {
            return true;
        }
        return fromPoint.isRedDiagonalPalace() && toPoint.isRedDiagonalPalace() && isDiagonalMovement(direction, 1);
    }

    private boolean isRedMovement(final Direction direction, final Point fromPoint, final Point toPoint) {
        if (isDownMovement(direction) || isHorizontalMovement(direction)) {
            return true;
        }
        return fromPoint.isGreenDiagonalPalace() && toPoint.isGreenDiagonalPalace() && isDiagonalMovement(direction,
                -1);
    }

    private static boolean isHorizontalMovement(final Direction direction) {
        return direction.horizontalDistance() == 1 && direction.verticalDistance() == 0;
    }

    private boolean isDiagonalMovement(final Direction direction, final int toDirection) {
        return direction.horizontalDistance() == 1 && direction.y() == toDirection;
    }

    private static boolean isUpMovement(final Direction direction) {
        return direction.isNotHorizontal() && direction.y() == 1;
    }

    private static boolean isDownMovement(final Direction direction) {
        return direction.isNotHorizontal() && direction.y() == -1;
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
