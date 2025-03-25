package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.passable.Bridge;
import janggi.movement.route.Hurdles;
import janggi.movement.passable.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.route.Route;
import java.util.ArrayList;
import java.util.List;

public class Po extends Movable {

    public Po(Team team, Point point) {
        super(team, point);
    }

    public static List<Po> init(Team team) {
        List<Po> pos = new ArrayList<>();
        for (int column = 1; column < MAX_COLUMN_LOCATION; column += 6) {
            pos.add(new Po(team, new Point(team.calculateRowForwarding(2), column)));
        }
        return pos;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        Direction direction = Direction.cardinalFrom(point, targetPoint);
        return isRouteHaveNoHurdle(targetPoint, hurdles, direction);
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, point, targetPoint);
        if (!route.hasCrash(hurdles)) {
            return false;
        }
        if (route.countCrashes(hurdles) > 1) {
            return false;
        }
        Bridge bridge = Bridge.from(route, hurdles, this);
        if (bridge.cannotPass()) {
            return false;
        }
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Po(team, afterPoint);
    }

    @Override
    public String getName() {
        return "포";
    }

    @Override
    public boolean isPo() {
        return true;
    }
}
