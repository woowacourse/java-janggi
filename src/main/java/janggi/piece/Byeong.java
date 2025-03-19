package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
import java.util.ArrayList;
import java.util.List;
import javax.print.attribute.standard.MediaSize.NA;

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
    public boolean isMovable(Point targetPoint) {
        List<Point> candidates = List.of(
                point.move(0, 1),
                point.move(0, -1),
                point.move(-1, 0)
        );
        return candidates.contains(targetPoint);
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        return List.of(targetPoint);
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

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Byeong(team, afterPoint);
    }
}
