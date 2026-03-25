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
        int pathX = to.getColumn() - from.getColumn();
        int pathY = to.getRow() - from.getRow();
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
                    Point.of(from.getColumn() + (pathX / DISTANCE_MAX), from.getRow()),
                    Point.of(from.getColumn() + signX * 2,  from.getRow() + signY)
            );
        }
        return List.of(
                Point.of(from.getColumn(), from.getRow() + (pathY / DISTANCE_MAX)),
                Point.of(from.getColumn() + signX,  from.getRow() + signY * 2));
    }
}
