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

public class Sa extends Movable {

    public Sa(Team team, Point point) {
        super(team, point);
    }

    public static List<Sa> init(Team team) {
        List<Sa> sas = new ArrayList<>();
        for (int column = 3; column < 6; column += 2) {
            sas.add(new Sa(team, new Point(team.calculateRowForwarding(0), column)));
        }
        return sas;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverFlow(targetPoint)) {
            return false;
        }
        Direction direction = Direction.toCardinalOrDiagonalFrom(point, targetPoint);
        if (!isRouteWithoutHurdle(targetPoint, hurdles, direction)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
    }

    private boolean isDistanceOverFlow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(1) && distance.notMatches(Math.sqrt(2));
    }

    private boolean isRouteWithoutHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, point, targetPoint);
        return !route.hasCrash(hurdles);
    }

    private boolean canMoveOrAttackTargetPoint(Point targetPoint, Hurdles hurdles) {
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
    }

    @Override
    public String getName() {
        return "사";
    }
}
