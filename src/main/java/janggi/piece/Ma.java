package janggi.piece;

import janggi.movement.direction.Direction;
import janggi.movement.middleRoute.Hurdles;
import janggi.movement.target.Prey;
import janggi.point.Point;
import janggi.game.Team;
import janggi.movement.distance.PointDistance;
import janggi.movement.middleRoute.Route;
import java.util.List;

public class Ma extends Piece {

    private static final int DIAGONAL_REPEAT_COUNT = 1;

    public Ma(Team team, Point point) {
        super(team, point, PieceInformation.MA);
    }

    public static List<Ma> init(Team team) {
        if (team.isCho()) {
            return List.of(
                    new Ma(team, new Point(9, 2)),
                    new Ma(team, new Point(9, 6))
            );
        }
        return List.of(
                new Ma(team, new Point(0, 1)),
                new Ma(team, new Point(0, 7))
        );
    }

    @Override
    public boolean canMove(Point targetPoint, Hurdles hurdles) {
        if (isDistanceOverflow(targetPoint)) {
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

    private boolean isDistanceOverflow(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        return distance.notMatches(PointDistance.oneDiagonalAndOneCardinal());
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
        return new Ma(team, afterPoint);
    }
}
