package janggi.point.crash;

import janggi.game.Team;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.Hurdles;
import janggi.point.Point;
import java.util.List;

public final class CrashOrPrey extends Crashes {

    public CrashOrPrey(List<Point> crashes) {
        super(crashes);
    }

    @Override
    public boolean hasNoCrashes(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        return isPreyOnly(movingTeam, targetPoint, hurdles);
    }

    private boolean isPreyOnly(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == 1) {
            Point preyPoint = crashes.getFirst();
            Movable preyPiece = hurdles.findByPoint(preyPoint);
            /**
             * 유일한 장애물이 먹이 위치에 있고
             * 팀이 다르면 isPreyOnly = true
             */
            if (!preyPoint.equals(targetPoint)) {
                return false;
            }
            if (movingTeam == preyPiece.getTeam()) {
                return false;
            }
            return true;
        }
        return false;
    }
}
