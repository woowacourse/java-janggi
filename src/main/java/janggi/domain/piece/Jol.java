package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jol extends BasePiece {

    private static final int MAX_DISTANCE = 1;

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getPathX(from);
        int pathY = to.getPathY(from);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        validateBackMove(pathY);

        if (isNormalMove(distanceX, distanceY) || isPalaceDiagonalMove(from, to, distanceX, distanceY)) {
            return List.of();
        }

        throw new IllegalArgumentException("졸은 앞으로, 좌우 한 칸 또는 궁성 안에서 대각선 한 칸만 이동할 수 있습니다.");
    }

    private boolean isNormalMove(int distanceX, int distanceY) {
        return distanceX + distanceY == MAX_DISTANCE;
    }

    private boolean isPalaceDiagonalMove(Point from, Point to, int distanceX, int distanceY) {
        return distanceX == 1
                && distanceY == 1
                && from.isPalaceDiagonalMove(to);
    }

    private void validateBackMove(int pathY) {
        if ((team == Team.CHO && pathY < 0) || (team == Team.HAN && pathY > 0)) {
            throw new IllegalArgumentException("뒤로 이동할 수 없습니다.");
        }
    }
}