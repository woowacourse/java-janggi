package janggi.movement.crash;

import janggi.game.Team;
import janggi.movement.route.Hurdles;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.Point;
import java.util.List;

public final class CrashOrBridgeAndPrey extends Crashes {
    public CrashOrBridgeAndPrey(List<Point> crashes) {
        super(crashes);
    }

    @Override
    public boolean hasNoCrashes(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        return isBridgeOnly(targetPoint, hurdles)
                || isBridgeAndPreyOnly(movingTeam, targetPoint, hurdles);
    }

    private boolean isBridgeOnly(Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == 1) {
            return isBridgeExists(targetPoint, hurdles);
        }
        return false;
    }

    private boolean isBridgeAndPreyOnly(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == 2) {
            return (isBridgeExists(targetPoint, hurdles)
                    && isPreyExists(movingTeam, targetPoint, hurdles));
        }
        return false;
    }

    private boolean isBridgeExists(Point targetPoint, Hurdles hurdles) {
        Point bridgePoint = crashes.getFirst();
        /**
         * 유일한 장애물일 때
         * 같은 포를 뛰어넘는 게 아니고,
         * 먹이 위치에 있지 않다면 isBridgeExists = true
         */
        if (hurdles.findByPoint(bridgePoint) instanceof Po) {
            return false;
        }
        if (bridgePoint.equals(targetPoint)) {
            return false;
        }
        return true;
    }

    private boolean isPreyExists(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        Point preyPoint = crashes.getLast();
        Movable prey = hurdles.findByPoint(preyPoint);
        /**
         * 2개 장애물 중 마지막 장애물이 먹이 위치에 있고
         * 같은 포를 먹지 않고,
         * 팀이 다르면 isPreyExists = true
         */
        if (prey instanceof Po) {
            return false;
        }
        if (!preyPoint.equals(targetPoint)) {
            return false;
        }
        if (prey.getTeam() == movingTeam) {
            return false;
        }
        return true;
    }
}
