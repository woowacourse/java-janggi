package janggi.piece;

import janggi.game.Palace;
import janggi.movement.direction.Direction;
import janggi.movement.middleRoute.Hurdles;
import janggi.movement.target.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.middleRoute.Route;
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
        if (isDistanceOutOfRange(targetPoint)) {
            return false;
        }
        Direction direction = Direction.toCardinalOrDiagonalFrom(point, targetPoint);
        if (isUnavailableDirection(targetPoint, direction)) {
            //TODO
        }
        if (isRouteCrashesHurdle(targetPoint, hurdles, direction)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
    }

    private boolean isDistanceOutOfRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(1) && distance.notMatches(Math.sqrt(2));
    }

    private boolean isUnavailableDirection(Point targetPoint, Direction direction) {
        if (Palace.movesInPalace(this, targetPoint)) {
            return !Palace.movesOnEdge(this, direction);
        }
        //TODO : false면 예외
        throw new IllegalArgumentException("사는 궁성 내에서만 이동 가능합니다.");
    }

    private boolean isRouteCrashesHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, point, targetPoint);
        return route.hasCrash(hurdles);
    }

    private boolean canMoveOrAttackTargetPoint(Point targetPoint, Hurdles hurdles) {
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sa(team, afterPoint);
    }

    @Override
    public String getName() {
        return "사";
    }
}
