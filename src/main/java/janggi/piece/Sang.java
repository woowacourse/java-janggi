package janggi.piece;

import janggi.Direction;
import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
import janggi.point.PointDistance;
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
    public String getName() {
        return NAME;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        List<Point> route = new ArrayList<>();
        List<Direction> directions = Direction.complexFrom(point, targetPoint, 3, 2);

        Point pointer = point;
        for (Direction direction : directions) {
            pointer = direction.move(pointer);
            route.add(pointer);
        }
        return route;

//        int rowGap = point.row() - targetPoint.row();
//        int columnGap = point.column() - targetPoint.column();
//        if (rowGap == 3) {
//            if (columnGap > 0) {
//                return List.of(new Point(point.row() - 1, point.column()), targetPoint.move(1, 1), targetPoint);
//            }
//            return List.of(new Point(point.row() - 1, point.column()), targetPoint.move(1, -1), targetPoint);
//        }
//
//        if (rowGap == -3) {
//            if (columnGap > 0) {
//                return List.of(new Point(point.row() + 1, point.column()), targetPoint.move(-1, 1), targetPoint);
//            }
//            return List.of(new Point(point.row() + 1, point.column()), targetPoint.move(-1, -1), targetPoint);
//        }
//
//        if (columnGap == 3) {
//            if (rowGap > 0) {
//                return List.of(new Point(point.row(), point.column() - 1), targetPoint.move(1, 1), targetPoint);
//            }
//            return List.of(new Point(point.row(), point.column() - 1), targetPoint.move(-1, 1), targetPoint);
//        }
//
//        if (rowGap > 0) {
//            return List.of(new Point(point.row(), point.column() + 1), targetPoint.move(1, -1), targetPoint);
//        }
//        return List.of(new Point(point.row(), point.column() + 1), targetPoint.move(-1, -1), targetPoint);
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(team, afterPoint);
    }
}
