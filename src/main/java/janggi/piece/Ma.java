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

public class Ma extends Movable {

    public Ma(Team team, Point point) {
        super(team, point);
    }

    public static List<Ma> init(Team team) {
        List<Ma> mas = new ArrayList<>();
        if (team.isCho()) {
            for (int column = 2; column < MAX_COLUMN_LOCATION; column += 4) {
                mas.add(new Ma(team, new Point(team.calculateRowForwarding(0), column)));
            }
            return mas;
        }
        for (int column = 1; column < MAX_COLUMN_LOCATION; column += 6) {
            mas.add(new Ma(team, new Point(team.calculateRowForwarding(0), column)));
        }
        return mas;
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverflow(targetPoint)) {
            return false;
        }
        List<Direction> directions = Direction.toInitialCardinalThenDiagonalFrom(
                point, targetPoint, 1
        );
        if (isRouteCrashesHurdle(hurdles, directions)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
    }

    private boolean isDistanceOverflow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(Math.sqrt(5));
    }

    private boolean isRouteCrashesHurdle(Hurdles hurdles, List<Direction> directions) {
        Route route = Route.follow(directions, point);
        return route.hasCrash(hurdles);
    }

    private boolean canMoveOrAttackTargetPoint(Point targetPoint, Hurdles hurdles) {
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Ma(team, afterPoint);
    }

    @Override
    public String getName() {
        return "마";
    }
}
