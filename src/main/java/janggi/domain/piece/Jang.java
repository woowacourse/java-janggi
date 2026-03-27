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
        int pathX = Point.getPathX(from, to);
        int pathY = Point.getPathY(from, to);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if ( distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX == 0 && distanceY == 0)) {
            throw new IllegalArgumentException();
        }
        return List.of(to);
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream()
                .noneMatch(piece -> piece.isSameTeam(team));
    }
}
