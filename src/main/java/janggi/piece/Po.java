package janggi.piece;

import janggi.game.Palace;
import janggi.movement.direction.Direction;
import janggi.movement.middleRoute.Bridge;
import janggi.movement.middleRoute.Hurdles;
import janggi.movement.target.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.middleRoute.Route;
import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

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
        Direction direction = Direction.toCardinalOrDiagonalFrom(point, targetPoint);
        if (isUnavailableDirection(targetPoint, direction)) {
            return false;
        }
        if (isRouteCrashesHurdle(targetPoint, hurdles, direction)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
    }

    private boolean isUnavailableDirection(Point targetPoint, Direction direction) {
        if (Palace.movesInPalace(point, targetPoint)) {
            return !Palace.movesOnEdge(point, direction);
        }
        return direction.isDiagonal();
    }

    private boolean isRouteCrashesHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, point, targetPoint);
        if (route.countCrashes(hurdles) != 1) {
            return true;
        }
        Bridge bridge = Bridge.from(route, hurdles, this);
        return bridge.cannotPass();
    }

    private boolean canMoveOrAttackTargetPoint(Point targetPoint, Hurdles hurdles) {
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Piece updatePoint(Point afterPoint) {
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
