package janggi.piece;

import janggi.game.Palace;
import janggi.movement.direction.Direction;
import janggi.movement.middleRoute.Hurdles;
import janggi.movement.target.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.middleRoute.Route;
import java.util.List;

public class Cha extends Piece {

    public Cha(Team team, Point point) {
        super(team, point, PieceInformation.CHA);
    }

    public static List<Cha> init(Team team) {
        if (team.isCho()) {
            return List.of(
                    new Cha(team, new Point(9, 0)),
                    new Cha(team, new Point(9, 8))
            );
        }
        return List.of(
                new Cha(team, new Point(0, 0)),
                new Cha(team, new Point(0, 8))
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
        return route.hasCrash(hurdles);
    }

    private boolean canMoveOrAttackTargetPoint(Point targetPoint, Hurdles hurdles) {
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Piece updatePoint(Point afterPoint) {
        return new Cha(team, afterPoint);
    }
}
