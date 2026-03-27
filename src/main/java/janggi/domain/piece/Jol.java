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
        int pathX = Point.getPathX(from, to);
        int pathY = Point.getPathY(from, to);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        validateOverMove(distanceX, distanceY);
        validateBackMove(pathY);

        return List.of(to);
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream()
                .noneMatch(piece -> piece.isSameTeam(team));
    }

    private void validateOverMove(int distanceX, int distanceY) {
        if (distanceX + distanceY != MAX_DISTANCE) {
            throw new IllegalArgumentException("한 칸만 이동할 수 있습니다.");
        }
    }

    private void validateBackMove(int pathY) {
        if ((team == Team.CHO && pathY < 0) || (team == Team.HAN && pathY > 0)) {
            throw new IllegalArgumentException("뒤로 이동할 수 없습니다.");
        }
    }
}

