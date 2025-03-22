package janggi.piece;

import janggi.game.Team;
import janggi.point.Direction;
import janggi.point.Point;
import janggi.point.PointDistance;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Movable {

    private static final String NAME = "마";

    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        return distance.isSameWith(Math.sqrt(5));
    }

    @Override
    public List<Point> findRoute(Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        List<Direction> directions = Direction.complexFrom(startPoint, targetPoint, 2, 1);

        Point pointer = startPoint;
        for (Direction direction : directions) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return route;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }
}
