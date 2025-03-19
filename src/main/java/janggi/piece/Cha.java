package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
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
    public boolean isMovable(Point targetPoint) {
        return point.isSameRow(targetPoint) || point.isSameColumn(targetPoint);
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
        if (point.isSameRow(targetPoint)) {
            if (point.isColumnBiggerThan(targetPoint)) {
                for (int column = point.column() - 1; column >= targetPoint.column(); column--) {
                    route.add(new Point(point.row(), column));
                }
            }
            if (point.isColumnLessThan(targetPoint)) {
                for (int column = point.column() + 1; column <= targetPoint.column(); column++) {
                    route.add(new Point(point.row(), column));
                }
            }

        }
        if (point.isSameColumn(targetPoint)) {
            if (point.isRowBiggerThan(targetPoint)) {
                for (int row = point.row() - 1; row >= targetPoint.row(); row--) {
                    route.add(new Point(row, point.column()));
                }
            }
            if (point.isRowLessThan(targetPoint)) {
                for (int row = point.row() + 1; row <= targetPoint.row(); row++) {
                    route.add(new Point(row, point.column()));
                }
            }
        }
        return route;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Cha(team, afterPoint);
    }
}
