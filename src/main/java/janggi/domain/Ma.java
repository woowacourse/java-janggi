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
        int pathX = to.getX() - from.getX();
        int pathY = to.getY() - from.getY();
        int distanceX = abs(pathX);
        int distanceY = abs(pathY);

        if (!((distanceX == DISTANCE_MAX && distanceY == DISTANCE_MIN) ||
                (distanceX == DISTANCE_MIN && distanceY == DISTANCE_MAX))) {
            throw new IllegalArgumentException();
        }
        if (distanceX == DISTANCE_MAX) {
            return List.of(Point.of(from.getX() + (pathX / DISTANCE_MAX), from.getY()));
        }
        return List.of(Point.of(from.getX(), from.getY() + (pathY / DISTANCE_MAX)));
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.isEmpty();
    }
}
