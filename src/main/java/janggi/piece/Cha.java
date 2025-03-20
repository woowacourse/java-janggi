package janggi.piece;

import janggi.point.Direction;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import java.util.ArrayList;
import java.util.List;

public class Cha implements Movable {

    private static final String NAME = "차";
    private static final List<Cha> chas;

    private final Team team;
    private final Point point;

    public Cha(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Cha> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.CHA.getRedPoints()) {
            createdPieces.add(new Cha(Team.HAN, point));
        }
        for (Point point : InitialPoint.CHA.getBluePoints()) {
            createdPieces.add(new Cha(Team.CHO, point));
        }
        chas = createdPieces;
    }

    public static List<Cha> values() {
        return new ArrayList<>(chas);
    }

    @Override
    public boolean isInMovingRange(Point targetPoint) {
        return point.isSameRow(targetPoint) || point.isSameColumn(targetPoint);
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        List<Point> route = new ArrayList<>();
        Direction direction = Direction.cardinalFrom(point, targetPoint);
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        Point pointer = point;
        for (int i = 0; i < (int) distance.getDistance(); i++) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return route;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Cha(team, afterPoint);
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
