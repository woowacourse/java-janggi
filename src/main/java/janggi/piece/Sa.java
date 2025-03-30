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

public class Sa extends Piece {

    public Sa(Team team, Point point) {
        super(team, point, PieceInformation.SA);
    }

    public static List<Sa> init(Team team) {
        if (team.isCho()) {
            return List.of(
                    new Sa(team, new Point(9, 3)),
                    new Sa(team, new Point(9, 5))
            );
        }
        return List.of(
                new Sa(team, new Point(0, 3)),
                new Sa(team, new Point(0, 5))
        );
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOutOfRange(targetPoint)) {
            return false;
        }
        Direction direction = Direction.toCardinalOrDiagonalFrom(point, targetPoint);
        if (isUnavailableDirection(targetPoint, direction)) {
            return false;
        }
        if (isRouteCrashesHurdle(targetPoint, hurdles, direction)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
    }

    private boolean isDistanceOutOfRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(PointDistance.oneCardinal()) && distance.notMatches(PointDistance.oneDiagonal());
    }

    private boolean isUnavailableDirection(Point targetPoint, Direction direction) {
        if (Palace.movesInPalaceOfMyTeam(this, targetPoint)) {
            return !Palace.movesOnEdge(point, direction);
        }
        return false;
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
    public Piece updatePoint(Point afterPoint) {
        return new Sa(team, afterPoint);
    }
}
