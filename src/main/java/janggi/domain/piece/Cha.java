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
        List<Point> route = new ArrayList<>();
        int pathX = Point.getPathX(from, to);
        int pathY = Point.getPathY(from, to);
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

    @Override
    public boolean canMove(List<Piece> route) {
        return route.isEmpty();
    }

    private void validateDiagonalMove(int pathX, int pathY) {
        if (pathX != 0 && pathY != 0) {
            throw new IllegalArgumentException("대각선 이동은 불가능합니다.");
        }
    }
}
