package janggi.piece;

import janggi.point.Direction;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import janggi.point.Route;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final String NAME = "상";
    private static final List<Sang> sangs;

    private final Team team;
    private final Point point;

    public Sang(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Sang> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.SANG.getRedPoints()) {
            createdPieces.add(new Sang(Team.HAN, point));
        }
        for (Point point : InitialPoint.SANG.getBluePoints()) {
            createdPieces.add(new Sang(Team.CHO, point));
        }
        sangs = createdPieces;
    }

    public static List<Sang> values() {
        return new ArrayList<>(sangs);
    }

    @Override
    public boolean isInMovingRange(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        return distance.isSameWith(Math.sqrt(13));
    }

    @Override
    public Route findRoute(Point targetPoint) {
        List<Direction> directions = Direction.complexFrom(point, targetPoint, 3, 2);
        return Route.follow(directions, this.point);
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
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
