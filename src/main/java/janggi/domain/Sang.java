package janggi.domain;

import static java.lang.Math.abs;

import java.util.List;

public class Sang implements Piece {

    private static final int DISTANCE_MAX = 3;
    private static final int DISTANCE_MIN = 2;

    private final Team team;

    public Sang(Team team) {
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
        int signX = Integer.compare(pathX, 0);
        int signY = Integer.compare(pathY, 0);

        if (!((distanceX == DISTANCE_MAX && distanceY == DISTANCE_MIN) ||
                (distanceX == DISTANCE_MIN && distanceY == DISTANCE_MAX))) {
            throw new IllegalArgumentException();
        }
        if (distanceX == DISTANCE_MAX) {
            return List.of(
                    Point.of(from.getX() + (pathX / DISTANCE_MAX), from.getY()),
                    Point.of(from.getX() + signX * DISTANCE_MIN,  from.getY() + signY)
            );
        }
        return List.of(
                Point.of(from.getX(), from.getY() + (pathY / DISTANCE_MAX)),
                Point.of(from.getX() + signX,  from.getY() + signY * DISTANCE_MIN));
    }

    @Override
    public boolean canMove(List<Piece> route) {
        return route.isEmpty();
    }
}
