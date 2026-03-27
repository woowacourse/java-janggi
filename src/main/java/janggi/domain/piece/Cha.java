package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha extends AbstractPiece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.calculatePathColumn(from);
        int pathY = to.calculatePathRow(from);

        if (pathY != 0 && pathX != 0) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 규칙에 어긋납니다.");
        }

        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);

        int distance = Math.max(abs(pathX), abs(pathY));

        List<Point> route = new ArrayList<>();

        for (int i = 1; i < distance; i++) {
            int nextX = from.getColumn() + (signX * i);
            int nextY = from.getRow() + (signY * i);
            route.add(Point.of(nextX, nextY));
        }
        return route;
    }
}
