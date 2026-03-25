package janggi.domain;

import static java.lang.Math.abs;

import java.util.List;

public class Ma implements Piece {

    private static final int DISTANCE_MAX = 2;
    private static final int DISTANCE_MIN = 1;

    private final Team team;

    public Ma(Team team) {
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
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (!((distanceX == DISTANCE_MAX && distanceY == DISTANCE_MIN) ||
                (distanceX == DISTANCE_MIN && distanceY == DISTANCE_MAX))) {
            throw new IllegalArgumentException();
        }
        if (distanceX == DISTANCE_MAX) {
            return List.of(Point.of(from.getColumn() + (pathX / DISTANCE_MAX), from.getRow()));
        }
        return List.of(Point.of(from.getColumn(), from.getRow() + (pathY / DISTANCE_MAX)));
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.isEmpty();
    }
}
