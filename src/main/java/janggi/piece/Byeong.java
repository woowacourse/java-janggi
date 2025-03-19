package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Byeong implements Movable {

    private static final String NAME = "병";
    private static final List<Byeong> byeongs;

    private final TeamColor color;
    private final Point point;

    public Byeong(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Byeong> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.BYEONG.getRedPoints()) {
            createdPieces.add(new Byeong(TeamColor.RED, point));
        }
        for (Point point : InitialPoint.BYEONG.getBluePoints()) {
            createdPieces.add(new Byeong(TeamColor.BLUE, point));
        }
        byeongs = createdPieces;
    }
    public static List<Byeong> values() {
        return new ArrayList<>(byeongs);
    }

    public boolean isMovable(Point targetPoint) {
        List<Point> candidates = List.of(
                point.move(0, 1),
                point.move(0, -1),
                point.move(-1, 0)
        );
        return candidates.contains(targetPoint);
    }

    public List<Point> findRoute(Point targetPoint) {
        return List.of(targetPoint);
    }

    @Override
    public TeamColor getColor() {
        return this.color;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Byeong(color, afterPoint);
    }
}
