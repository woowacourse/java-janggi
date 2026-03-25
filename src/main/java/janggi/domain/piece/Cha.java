package janggi.domain.piece;

import janggi.domain.Point;
import janggi.domain.status.Team;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private final Team team;
    private final PieceType type;

    public Cha(Team team) {
        this.team = team;
        this.type = PieceType.CHA;
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public List<Point> getRoute(Point from, Point to) {
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();
        List<Point> route = new ArrayList<>();

        if (pathY != 0 && pathX != 0) {
            throw new IllegalArgumentException();
        }

        if (pathX == 0) {
            if (from.getY() > to.getY()) {
                for (int i = to.getY() + 1; i < from.getY(); i++) {
                    route.add(Point.of(from.getX(), i));
                }
                return route;
            }
            for (int i = from.getY() + 1; i < to.getY(); i++) {
                route.add(Point.of(from.getX(), i));
            }
            return route;
        }

        if (from.getX() > to.getX()) {
            for (int i = to.getX() + 1; i < from.getX(); i++) {
                route.add(Point.of(i, from.getY()));
            }
            return route;
        }
        for (int i = from.getX() + 1; i < to.getX(); i++) {
            route.add(Point.of(i, from.getY()));
        }
        return route;
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return false;
    }

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }
}
