package janggi.piece;

import janggi.game.Team;
import janggi.point.Direction;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Movable {

    private static final String NAME = "차";

    private final Team team;

    public Cha(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        return startPoint.isSameRow(targetPoint) || startPoint.isSameColumn(targetPoint);
    }

    @Override
    public Route findRoute(Point startPoint, Point targetPoint) {
        List<Point> route = new ArrayList<>();
        Direction direction = Direction.cardinalFrom(startPoint, targetPoint);
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        Point pointer = startPoint;
        for (int i = 0; i < (int) distance.getDistance() - 1; i++) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return new Route(route, targetPoint);
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
