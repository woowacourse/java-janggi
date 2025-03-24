package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.route.Hurdles;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.route.Route;
import java.util.ArrayList;
import java.util.List;

public class Gung extends Movable {

    public Gung(Team team, Point point) {
        super(team, point);
    }

    public static List<Gung> init(Team team) {
        List<Gung> gungs = new ArrayList<>();
        gungs.add(new Gung(team, new Point(team.calculateRowForwarding(1), 4)));
        return gungs;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverFlow(targetPoint)) {
            return false;
        }
        Direction direction = Direction.cardinalOrDiagonalFrom(point, targetPoint);
        return isRouteHaveNoHurdle(targetPoint, hurdles, direction);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return !distance.isSameWith(1) && !distance.isSameWith(Math.sqrt(2));
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, point, targetPoint);
        return route.hasNoHurdle(this, targetPoint, hurdles);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Gung(team, afterPoint);
    }

    @Override
    public String getName() {
        return "궁";
    }
}
