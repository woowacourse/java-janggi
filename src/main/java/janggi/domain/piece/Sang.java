package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Sang extends BasePiece {

    private static final int DISTANCE_MAX = 3;
    private static final int DISTANCE_MIN = 2;

    public Sang(Team team) {
        super(team, PieceType.SANG);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);
        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);

        validateMove(distanceX, distanceY);

        if (distanceX == DISTANCE_MAX) {
            return List.of(
                    Point.of(from.getX() + (pathX / DISTANCE_MAX), from.getY()),
                    Point.of(from.getX() + signX * DISTANCE_MIN,  from.getY() + signY)
            );
        }
        return List.of(
                Point.of(from.getX(), from.getY() + (pathY / DISTANCE_MAX)),
                Point.of(from.getX() + signX,  from.getY() + signY * DISTANCE_MIN)
        );
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.isEmpty();
    }

    private void validateMove(int distanceX, int distanceY) {
        if (!((distanceX == DISTANCE_MAX && distanceY == DISTANCE_MIN) ||
                (distanceX == DISTANCE_MIN && distanceY == DISTANCE_MAX))) {
            throw new IllegalArgumentException("해당 기물의 이동 경로의 규칙에 어긋납니다.");
        }
    }
}
