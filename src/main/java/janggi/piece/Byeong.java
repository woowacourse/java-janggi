package janggi.piece;

import janggi.game.Palace;
import janggi.movement.direction.Direction;
import janggi.movement.middleRoute.Hurdles;
import janggi.movement.target.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.middleRoute.Route;
import java.util.List;

public class Byeong extends Piece {

    public static List<Byeong> init(Team team) {
        if (team.isCho()) {
            return List.of(
                    new Byeong(team, new Point(6, 0)),
                    new Byeong(team, new Point(6, 2)),
                    new Byeong(team, new Point(6, 4)),
                    new Byeong(team, new Point(6, 6)),
                    new Byeong(team, new Point(6, 8))
            );
        }
        return List.of(
                new Byeong(team, new Point(3, 0)),
                new Byeong(team, new Point(3, 2)),
                new Byeong(team, new Point(3, 4)),
                new Byeong(team, new Point(3, 6)),
                new Byeong(team, new Point(3, 8))
        );
    }

    public Byeong(Team team, Point point) {
        super(team, point, PieceInformation.BYEONG);
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOutOfRange(targetPoint)) {
            return false;
        }
        Direction direction = Direction.toCardinalOrDiagonalFrom(point, targetPoint);
        if (movesDown(direction)) {
            return false;
        }
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
        return distance.notMatches(PointDistance.oneCardinal());
    }

    private boolean movesDown(Direction direction) {
        return direction.isBackwardDirectionOf(team);
    }

    private boolean isUnavailableDirection(Point targetPoint, Direction direction) {
        if (Palace.movesInPalace(point, targetPoint)) {
            return !Palace.movesOnEdge(point, direction);
        }
        return direction.isDiagonal();
    }

    private boolean isRouteCrashesHurdle(Point targetPoint, Hurdles hurdles, Direction direction) {
        Route route = Route.repeat(direction, this.point, targetPoint);
        return route.hasCrash(hurdles);
    }

    private boolean canMoveOrAttackTargetPoint(Point targetPoint, Hurdles hurdles) {
        Prey prey = Prey.from(targetPoint, hurdles, this);
        return prey.canAttack();
    }

    @Override
    public Piece updatePoint(Point afterPoint) {
        return new Byeong(team, afterPoint);
    }
}
