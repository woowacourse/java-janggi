package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
import java.util.List;

public class Ma extends AbstractPiece {

    private static final int LONG_STEP = 2;
    private static final int SHORT_STEP = 1;

    public Ma(Team team) {
        super(team, PieceType.MA);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathX = to.calculatePathColumn(from);
        int pathY = to.calculatePathRow(from);
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (!((distanceX == LONG_STEP && distanceY == SHORT_STEP) ||
                (distanceX == SHORT_STEP && distanceY == LONG_STEP))) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }
        if (distanceX == LONG_STEP) {
            return new Points(List.of(Point.of(from.getColumn() + (pathX / LONG_STEP), from.getRow())));
        }
        return new Points(List.of(Point.of(from.getColumn(), from.getRow() + (pathY / LONG_STEP))));
    }
}
