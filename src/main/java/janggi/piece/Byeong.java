package janggi.piece;

import janggi.game.Team;
import janggi.point.Point;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.List;

public class Byeong implements Movable {

    private static final String NAME = "병";

    private final Team team;

    public Byeong(Team team) {
        this.team = team;
    }

    @Override
    public boolean isInMovingRange(Point startPoint, Point targetPoint) {
        PointDistance distance = PointDistance.calculate(startPoint, targetPoint);

        if (team == Team.CHO) {
            return distance.isSameWith(1) && !startPoint.isRowLessThan(targetPoint);
        }
        return distance.isSameWith(1) && !startPoint.isRowBiggerThan(targetPoint);
    }

    @Override
    public Route findRoute(Point startPoint, Point targetPoint) {
        return new Route(List.of(), targetPoint);
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
