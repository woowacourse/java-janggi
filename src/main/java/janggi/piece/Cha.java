package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.route.Hurdles;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.route.Route;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Movable {

    private static final String NAME = "차";

    private final Team team;
    private final Point point;

    public Cha(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    public static List<Cha> init(Team team) {
        List<Cha> chas = new ArrayList<>();
        for (int column = 0; column < 9; column += 8) {
            chas.add(new Cha(team, new Point(team.calculateRowForwarding(0), column)));
        }
        return chas;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        Direction direction = Direction.cardinalFrom(this.point, targetPoint);
        return isRouteHaveNoHurdle(targetPoint, hurdles, direction);
    }

    private boolean isRouteHaveNoHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, this.point, targetPoint);
        return route.hasNoHurdle(this, targetPoint, hurdles);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Cha(team, afterPoint);
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
