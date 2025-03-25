package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.route.Hurdles;
import janggi.movement.passable.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.route.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Movable {

    public Sang(Team team, Point point) {
        super(team, point);
    }

    public static List<Sang> init(Team team) {
        List<Sang> sangs = new ArrayList<>();
        if (team.isCho()) {
            for (int column = 1; column < MAX_COLUMN_LOCATION; column += 6) {
                sangs.add(new Sang(team, new Point(team.calculateRowForwarding(0), column)));
            }
            return sangs;
        }
        for (int column = 2; column < MAX_COLUMN_LOCATION; column += 4) {
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
                point, targetPoint, 2
        );
        return isRouteHaveNoHurdle(targetPoint, hurdles, directions);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(Math.sqrt(13));
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, List<Direction> directions) {
        Route route = Route.follow(directions, point);
        if (route.hasCrash(hurdles)) {
            return false;
        }
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
    }

    @Override
    public String getName() {
        return "상";
    }
}
