package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Sa extends BasePiece {

    private static final int MAX_DISTANCE = 1;

    public Sa(Team team) {
        super(team, PieceType.SA);
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = Point.getPathX(from, to);
        int pathY = Point.getPathY(from, to);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        validateOverMove(distanceX, distanceY);

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
}
