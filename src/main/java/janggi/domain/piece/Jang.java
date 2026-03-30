package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.piece.direction.CastleDirection;
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
        if (!from.inSameCastle(to)) {
            throw new IllegalArgumentException("[ERROR] 장은 궁성 밖으로 나갈 수 없습니다.");
        }
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);
        int distanceCol = abs(pathCol);
        int distanceRow = abs(pathRow);

        if (distanceCol > MAX_DISTANCE || distanceRow > MAX_DISTANCE || (distanceCol == 0 && distanceRow == 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        CastleDirection direction = CastleDirection.find(from, pathCol, pathRow);
        Point point = Point.of(from.getColumn() + direction.getTargetCol(), from.getRow() + direction.getTargetRow());
        return new Points(List.of(point));
    }

    @Override
    public boolean canMove(Route route) {
        return !route.hasAlly(super.getTeam());
    }
}
