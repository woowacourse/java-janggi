package janggi.piece;

import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import java.util.ArrayList;
import java.util.List;

public class Gung implements Movable {

    private static final String NAME = "궁";

    private final Team team;
    private final Point point;

    public Gung(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    @Override
    public boolean isInMovingRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        return distance.isSameWith(1);
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        return List.of(targetPoint);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Gung(team, afterPoint);
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
