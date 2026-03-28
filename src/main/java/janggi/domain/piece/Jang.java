package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.List;

public class Jang extends AbstractPiece {

    private static final int MAX_DISTANCE = 1;

    public Jang(Team team) {
        super(team, PieceType.JANG);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathX = to.calculatePathColumn(from);
        int pathY = to.calculatePathRow(from);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX == 0 && distanceY == 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        return new Points(List.of(to));
    }

    @Override
    public boolean canMove(Route route) {
        return !route.hasAlly(super.getTeam());
    }
}
