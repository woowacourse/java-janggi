package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.piece.direction.CastleDirection;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.List;

public class Jol extends AbstractPiece {

    private static final int SCORE = 2;
    private static final int MAX_DISTANCE = 1;

    public Jol(Team team) {
        super(SCORE, team, PieceType.JOL);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);
        int signCol = Integer.compare(pathCol, 0);
        int signRow = Integer.compare(pathRow, 0);
        validateForward(signRow);
        int distanceCol = abs(pathCol);
        int distanceRow = abs(pathRow);
        if (from.inSameCastle(to)) {
            CastleDirection direction = CastleDirection.find(from, signCol, signRow);
            Point point = Point.of(from.getColumn() + direction.getTargetCol(),
                    from.getRow() + direction.getTargetRow());
            return new Points(List.of(point));
        }
        validateDistance(distanceCol, distanceRow);
        return new Points(List.of(to));
    }

    @Override
    public boolean canMove(Route route) {
        return !route.hasAlly(super.getTeam());
    }

    private void validateForward(int signRow) {
        if ((super.getTeam().equals(Team.CHO) && signRow < 0) || (super.getTeam().equals(Team.HAN) && signRow > 0)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
    }

    private void validateDistance(int distanceCol, int distanceRow) {
        if (distanceCol > MAX_DISTANCE || distanceRow > MAX_DISTANCE || (distanceCol + distanceRow > MAX_DISTANCE)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
    }
}
