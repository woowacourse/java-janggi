package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.List;

public class Jang extends BasePiece {

    private static final int MAX_DISTANCE = 1;

    public Jang(Team team) {
        super(team, PieceType.JANG);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getPathX(from);
        int pathY = to.getPathY(from);
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
