package janggi.domain;

import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private final Team team;

    public Cha(Team team) {
        this.team = team;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getColumn() - from.getColumn();
        int pathY = to.getRow() - from.getRow();
        List<Point> route = new ArrayList<>();

        if (pathY != 0 && pathX != 0) {
            throw new IllegalArgumentException();
        }

        if (pathX == 0) {
            if (from.getRow() > to.getRow()) {
                for (int i = to.getRow() + 1; i < from.getRow(); i++) {
                    route.add(Point.of(from.getColumn(), i));
                }
                return route;
            }
            for (int i = from.getRow() + 1; i < to.getRow(); i++) {
                route.add(Point.of(from.getColumn(), i));
            }
            return route;
        }

        if (from.getColumn() > to.getColumn()) {
            for (int i = to.getColumn() + 1; i < from.getColumn(); i++) {
                route.add(Point.of(i, from.getRow()));
            }
            return route;
        }
        for (int i = from.getColumn() + 1; i < to.getColumn(); i++) {
            route.add(Point.of(i, from.getRow()));
        }
        return route;
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return false;
    }
}
