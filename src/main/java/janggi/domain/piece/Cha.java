package janggi.domain.piece;

import static java.lang.Math.abs;

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

        if (pathY != 0 && pathX != 0) {
            throw new IllegalArgumentException();
        }

        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);

        int distance = Math.max(abs(pathX), abs(pathY));

        List<Point> route = new ArrayList<>();

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

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }

    @Override
    public PieceType getType() {
        return type;
    }
}
