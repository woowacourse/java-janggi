package janggi.point;

import janggi.game.Team;
import janggi.piece.Movable;
import janggi.piece.Po;
import java.util.List;

public class Crashes {
    private final List<Point> crashes;

    public Crashes(List<Point> crashes) {
        this.crashes = crashes;
    }

    public boolean isPresent() {
        return !crashes.isEmpty();
    }

    //Po 제외
    public boolean isPreyOnly(Team movingTeam, Point targetPoint, Hurdles hurdles) {
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

    //Po 적용
    //TODO Crashes 객체 둘로 나누기
    public boolean isBridgeOnly(Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == 1 || crashes.size() == 2) {
            return isBridgeExists(targetPoint, hurdles);
        }
        return false;
    }

    public boolean isBridgeAndPreyOnly(Team movingTeam, Point targetPoint, Hurdles hurdles) {
        if (crashes.size() == 2) {
            return (isPreyExists(movingTeam, targetPoint, hurdles));
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
        if (prey instanceof Po) { //TODO 수정 - prey.canAttackSameTeam으로?
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
