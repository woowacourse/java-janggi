package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.List;

public class Jol extends AbstractPiece {

    private static final int MAX_DISTANCE = 1;

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);
        int signRow = Integer.compare(pathRow, 0);
        int distanceCol = abs(pathCol);
        int distanceRow = abs(pathRow);

        if (distanceCol > MAX_DISTANCE || distanceRow > MAX_DISTANCE || (distanceCol + distanceRow > MAX_DISTANCE)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        if ((super.getTeam().equals(Team.CHO) && signRow < 0) || (super.getTeam().equals(Team.HAN) && signRow > 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        return new Points(List.of(to));
    }

    @Override
    public boolean canMove(Route route) {
        return !route.hasAlly(super.getTeam());
    }
}
