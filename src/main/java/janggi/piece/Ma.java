package janggi.piece;

import janggi.point.Direction;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Movable {

    private static final String NAME = "마";

    private final Team team;
    private final Point point;

    public Ma(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    @Override
    public boolean isInMovingRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        return distance.isSameWith(Math.sqrt(5));
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        List<Point> route = new ArrayList<>();
        List<Direction> directions = Direction.complexFrom(point, targetPoint, 2, 1);

        Point pointer = point;
        for (Direction direction : directions) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return route;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Ma(team, afterPoint);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }
}
