package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.route.Hurdles;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.route.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final String NAME = "상";

    private final Team team;
    private final Point point;

    public Sang(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    public static List<Sang> init(Team team) {
        List<Sang> sangs = new ArrayList<>();
        if (team.isCho()) {
            for (int column = 1; column < 9; column += 6) {
                sangs.add(new Sang(team, new Point(team.calculateRowForwarding(0), column)));
            }
            return sangs;
        }
        for (int column = 2; column < 9; column += 4) {
            sangs.add(new Sang(team, new Point(team.calculateRowForwarding(0), column)));
        }
        return sangs;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverFlow(targetPoint)) {
            return false;
        }
        List<Direction> directions = Direction.oneCardinalAndRepeatingDiagonalFrom(
                this.point, targetPoint, 2
        );
        return isRouteHaveNoHurdle(targetPoint, hurdles, directions);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        if (!distance.isSameWith(Math.sqrt(13))) {
            return true;
        }
        return false;
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, List<Direction> directions) {
        Route route = Route.follow(directions, this.point);
        return route.hasNoHurdle(this, targetPoint, hurdles);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
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
