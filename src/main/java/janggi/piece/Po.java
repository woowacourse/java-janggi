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

    private static final int BRIDGE_COUNT = 1;

    public Po(Team team, Point point) {
        super(team, point, PieceInformation.PO);
    }

    public static List<Po> init(Team team) {
        if (team.isCho()) {
            return List.of(
                    new Po(team, new Point(7, 1)),
                    new Po(team, new Point(7, 7))
            );
        }
        return List.of(
                new Po(team, new Point(2, 1)),
                new Po(team, new Point(2, 7))
        );
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
        if (route.countCrashes(hurdles) != BRIDGE_COUNT) {
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
    public boolean isPo() {
        return true;
    }
}
