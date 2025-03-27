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
        return isGreenForward(direction) || isRedForward(direction) || isHorizontalMovement(direction);
    }

    private boolean isRedForward(final Direction direction) {
        return Team.RED == team && isDownMovement(direction);
    }

    private boolean isGreenForward(final Direction direction) {
        return Team.GREEN == team && isUpMovement(direction);
    }

    private static boolean isDownMovement(final Direction direction) {
        return direction.isNotHorizontal() && direction.y() == -1;
    }

    private static boolean isUpMovement(final Direction direction) {
        return direction.isNotHorizontal() && direction.y() == 1;
    }

    private static boolean isHorizontalMovement(final Direction direction) {
        return direction.horizontalDistance() == 1 && direction.verticalDistance() == 0;
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
