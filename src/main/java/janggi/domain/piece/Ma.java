package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Ma extends BasePiece {

    private static final int DISTANCE_MAX = 2;
    private static final int DISTANCE_MIN = 1;

    public Ma(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = Point.getPathX(from, to);
        int pathY = Point.getPathY(from, to);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        validateMove(distanceX, distanceY);

        if (distanceX == DISTANCE_MAX) {
            return List.of(Point.of(from.getX() + (pathX / DISTANCE_MAX), from.getY()));
        }
        return List.of(Point.of(from.getX(), from.getY() + (pathY / DISTANCE_MAX)));
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
