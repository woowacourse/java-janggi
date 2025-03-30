package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.middleRoute.Hurdles;
import janggi.movement.target.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.middleRoute.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    private static final int DIAGONAL_REPEAT_COUNT = 2;

    public Sang(Team team, Point point) {
        super(team, point, PieceInformation.SANG);
    }

    public static List<Sang> init(Team team) {
        if (team.isCho()) {
            return List.of(
                    new Sang(team, new Point(9, 1)),
                    new Sang(team, new Point(9, 7))
            );
        }
        return List.of(
                new Sang(team, new Point(0, 2)),
                new Sang(team, new Point(0, 6))
        );
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOutOfRange(targetPoint)) {
            return false;
        }
        List<Direction> directions = Direction.toInitialCardinalThenDiagonalFrom(
                point, targetPoint, DIAGONAL_REPEAT_COUNT
        );
        if (isRouteCrashesHurdle(hurdles, directions)) {
            return false;
        }
        return canMoveOrAttackTargetPoint(targetPoint, hurdles);
    }

    private boolean isDistanceOutOfRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(PointDistance.oneDiagonalAndTwoCardinal());
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
    public Piece updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
    }
}
