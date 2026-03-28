package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha extends AbstractPiece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public Points getRoutePoints(Point from, Point to) {
        int pathCol = to.calculatePathColumn(from);
        int pathRow = to.calculatePathRow(from);

        if (pathRow != 0 && pathCol != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }

        int signCol = Integer.compare(pathCol, 0);
        int signRow = Integer.compare(pathRow, 0);

        int distance = Math.max(abs(pathCol), abs(pathRow));

        List<Point> route = new ArrayList<>();

        for (int i = 1; i < distance; i++) {
            int nextCol = from.getColumn() + (signCol * i);
            int nextRow = from.getRow() + (signRow * i);
            route.add(Point.of(nextCol, nextRow));
        }
        return new Points(route);
    }
}
