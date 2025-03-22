package janggi.piece;

import janggi.point.Direction;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Ma implements Movable {

    private static final String NAME = "마";
    private static final List<Ma> mas;

    private final Team team;
    private final Point point;

    public Ma(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Ma> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.MA.getRedPoints()) {
            createdPieces.add(new Ma(Team.HAN, point));
        }
        for (Point point : InitialPoint.MA.getBluePoints()) {
            createdPieces.add(new Ma(Team.CHO, point));
        }
        mas = createdPieces;
    }

    public static List<Ma> values() {
        return new ArrayList<>(mas);
    }

    @Override
    public boolean isInMovingRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        return distance.isSameWith(Math.sqrt(5));
    }

    @Override
    public Route findRoute(Point targetPoint) {
        List<Direction> directions = Direction.complexFrom(point, targetPoint, 2, 1);
        return Route.follow(directions, this.point);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Ma(team, afterPoint);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }
}
