package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Pho extends AbstractPiece {

    public Pho(Team team) {
        super(team, PieceType.PHO);
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

    @Override
    public boolean canMove(Route route) {
        if (route.hasObstacle()) {
            return false;
        }
        return !route.hasSameType(PieceType.PHO);
    }

    @Override
    public boolean canCapture(Piece target) {
        return !target.isSameType(PieceType.PHO);
    }
}
