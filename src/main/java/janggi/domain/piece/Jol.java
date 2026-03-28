package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.List;

public class Jol extends AbstractPiece {

    private static final int MAX_DISTANCE = 1;

    public Jol(Team team) {
        super(team, PieceType.JOL);
    }

    @Override
    public Route getRoute(Point from, Point to) {
        int pathX = to.calculatePathColumn(from);
        int pathY = to.calculatePathRow(from);
        int signY = Integer.compare(pathY, 0);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (distanceX > MAX_DISTANCE || distanceY > MAX_DISTANCE || (distanceX + distanceY > MAX_DISTANCE)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        if ((super.getTeam().equals(Team.CHO) && signY < 0) || (super.getTeam().equals(Team.HAN) && signY > 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        return new Route(List.of(to));
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.stream().noneMatch(piece -> piece.isSameTeam(super.getTeam()));
    }
}
