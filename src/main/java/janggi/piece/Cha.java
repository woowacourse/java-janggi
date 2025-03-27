package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.route.Hurdles;
import janggi.movement.passable.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.route.Route;
import java.util.ArrayList;
import java.util.List;

public class Cha extends Movable {

    public Cha(Team team, Point point) {
        super(team, point);
    }

    public static List<Cha> init(Team team) {
        List<Cha> chas = new ArrayList<>();
        for (int column = 0; column < MAX_COLUMN_LOCATION; column += 8) {
            chas.add(new Cha(team, new Point(team.calculateRowForwarding(0), column)));
        }
        return chas;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        Direction direction = Direction.toCardinalFrom(point, targetPoint);
        if (!isRouteWithoutHurdle(targetPoint, hurdles, direction)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
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
        return new Cha(team, afterPoint);
    }

    @Override
    public String getName() {
        return "차";
    }
}
