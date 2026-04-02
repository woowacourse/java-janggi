package janggi.domain.piece;

import static java.lang.Math.abs;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha extends BasePiece {

    public Cha(Team team) {
        super(team, PieceType.CHA);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        if (isStraightMove(from, to)) {
            return getNormalRoute(from, to);
        }
        if (from.isPalaceDiagonalMove(to)) {
            return getPalaceRoute(from, to);
        }
        throw new IllegalArgumentException("차는 직선 또는 궁성 대각선으로만 이동할 수 있습니다.");
    }

    private boolean isStraightMove(Point from, Point to) {
        int pathX = to.getPathX(from);
        int pathY = to.getPathY(from);
        return pathX == 0 || pathY == 0;
    }

    private List<Point> getNormalRoute(Point from, Point to) {
        List<Point> route = new ArrayList<>();
        int pathX = to.getPathX(from);
        int pathY = to.getPathY(from);
        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);
        int distance = Math.max(abs(pathX), abs(pathY));

        validateDiagonalMove(pathX, pathY);

        for (int i = 1; i < distance; i++) {
            int nextX = from.getX() + (signX * i);
            int nextY = from.getY() + (signY * i);
            route.add(Point.of(nextX, nextY));
        }
        return route;
    }

    private List<Point> getPalaceRoute(Point from, Point to) {
        int pathX = abs(to.getPathX(from));
        int pathY = abs(to.getPathY(from));

        if (pathX == 1 && pathY == 1) {
            return List.of();
        }
        if (pathX == 2 && pathY == 2) {
            int middleX = (from.getX() + to.getX()) / 2;
            int middleY = (from.getY() + to.getY()) / 2;
            return List.of(Point.of(middleX, middleY));
        }
        throw new IllegalArgumentException("궁성 대각선 경로가 아닙니다.");
    }

    private void validateDiagonalMove(int pathX, int pathY) {
        if (pathX != 0 && pathY != 0) {
            throw new IllegalArgumentException("대각선 이동은 불가능합니다.");
        }
    }
}
