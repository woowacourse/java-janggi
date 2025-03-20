package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
import janggi.point.PointDistance;
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
    public boolean isMovable(Point targetPoint) {
        PointDistance distance = PointDistance.calculate(point, targetPoint);

        return distance.isSameWith(Math.sqrt(5));
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
        int rowGap = point.row() - targetPoint.row();
        int columnGap = point.column() - targetPoint.column();
        if (rowGap == 2) {
            return List.of(new Point(point.row() - 1, point.column()),targetPoint);
        }
        if (rowGap == -2) {
            return List.of(new Point(point.row() + 1, point.column()),targetPoint);
        }
        if (columnGap == 2) {
            return List.of(new Point(point.row(), point.column() - 1),targetPoint);
        }
        return List.of(new Point(point.row(), point.column() + 1),targetPoint);
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Ma(team, afterPoint);
    }
}
