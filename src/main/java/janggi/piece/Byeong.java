package janggi.piece;

import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
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
    public boolean isInMovingRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        if (team == Team.CHO) {
            return distance.isSameWith(1) && !point.isRowLessThan(targetPoint);
        }
        return distance.isSameWith(1) && !point.isRowBiggerThan(targetPoint);
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        return List.of(targetPoint);
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
