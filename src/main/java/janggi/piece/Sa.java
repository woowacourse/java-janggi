package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.PointDistance;
import java.util.List;

public class Sa implements Movable {

    private static final String NAME = "사";

    private final Team team;

    public Sa(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        return distance.isSameWith(1);
    }

    @Override
    public List<Point> findRoute(Point startPoint, Point targetPoint) {
        return List.of();
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
