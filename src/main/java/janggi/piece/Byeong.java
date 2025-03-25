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

public class Byeong extends Movable {

    public static List<Byeong> init(Team team) {
        List<Byeong> byeongs = new ArrayList<>();
        for (int column = 0; column < MAX_COLUMN_LOCATION; column += 2) {
            byeongs.add(new Byeong(team, new Point(team.calculateRowForwarding(3), column)));
        }
        return byeongs;
    }

    public Byeong(Team team, Point point) {
        super(team, point);
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverFlow(targetPoint)) {
            return false;
        }
        Direction direction = Direction.cardinalFrom(point, targetPoint);
        if (movesDown(direction)) {
            return false;
        }
        return isRouteHaveNoHurdle(targetPoint, hurdles, direction);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(1);
    }

    private boolean movesDown(Direction direction) {
        return team.headsBack(direction);
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, this.point, targetPoint);
        if (route.hasCrash(hurdles)) {
            return false;
        }
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Byeong(team, afterPoint);
    }

    @Override
    public String getName() {
        return "병";
    }
}
