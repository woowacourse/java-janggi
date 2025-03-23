package janggi.piece;

import janggi.point.Crashes;
import janggi.point.Direction;
import janggi.point.Hurdles;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Byeong implements Movable {

    private static final String NAME = "병";
    private static final List<Byeong> byeongs;

    private final Team team;
    private final Point point;

    public Byeong(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Byeong> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.BYEONG.getRedPoints()) {
            createdPieces.add(new Byeong(Team.HAN, point));
        }
        for (Point point : InitialPoint.BYEONG.getBluePoints()) {
            createdPieces.add(new Byeong(Team.CHO, point));
        }
        byeongs = createdPieces;
    }

    public static List<Byeong> values() {
        return new ArrayList<>(byeongs);
    }

    @Override
    public boolean isInMovingRange(Point targetPoint, Hurdles hurdles) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);
        if (!distance.isSameWith(1)) {
            return false;
        }
        Direction direction = Direction.cardinalFrom(this.point, targetPoint);

        if (movesDown(direction)) {
            return false;
        }

        //장애물 체크
        Route route = Route.repeat(direction, this.point, targetPoint);
        if (route.isCrashExists(hurdles)) {
            Crashes crashPoints = route.findCrashes(hurdles);
            return crashPoints.isPreyOnly(this.team, targetPoint, hurdles);
        }
        return true;
    }

    private boolean movesDown(Direction direction) {
        if (team == Team.CHO && direction == Direction.SOUTH) {
            return true;
        }
        if (team == Team.HAN && direction == Direction.NORTH) {
            return true;
        }
        return false;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Byeong(team, afterPoint);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Point getPoint() {
        return point;
    }
}
