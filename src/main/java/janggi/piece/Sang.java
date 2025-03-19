package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Movable {

    private static final String NAME = "상";
    private static final List<Sang> sangs;

    private final TeamColor color;
    private final Point point;

    public Sang(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Sang> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.SANG.getRedPoints()) {
            createdPieces.add(new Sang(TeamColor.RED, point));
        }
        for (Point point : InitialPoint.SANG.getBluePoints()) {
            createdPieces.add(new Sang(TeamColor.BLUE, point));
        }
        sangs = createdPieces;
    }
    public static List<Sang> values() {
        return new ArrayList<>(sangs);
    }

    @Override
    public boolean isMovable(Point targetPoint) {
        List<Point> candidates = List.of(
                point.move(-2, 3),
                point.move(-3, 2),
                point.move(3, 2),
                point.move(2, 3),
                point.move(2, -3),
                point.move(3, -2),
                point.move(-3, -2),
                point.move(-2, -3)
        );

        return candidates.contains(targetPoint);
    }

    @Override
    public Point getPoint() {
        return point;
    }

    public List<Point> findRoute(Point targetPoint) {
        int rowGap = point.row() - targetPoint.row();
        int columnGap = point.column() - targetPoint.column();
        if (rowGap == 3) {
            if (columnGap > 0) {
                return List.of(new Point(point.row() - 1, point.column()), targetPoint.move(1, 1), targetPoint);
            }
            return List.of(new Point(point.row() - 1, point.column()), targetPoint.move(1, -1), targetPoint);
        }

        if (rowGap == -3) {
            if (columnGap > 0) {
                return List.of(new Point(point.row() + 1, point.column()), targetPoint.move(-1, 1), targetPoint);
            }
            return List.of(new Point(point.row() + 1, point.column()), targetPoint.move(-1, -1), targetPoint);
        }

        if (columnGap == 3) {
            if (rowGap > 0) {
                return List.of(new Point(point.row(), point.column() - 1), targetPoint.move(1, 1), targetPoint);
            }
            return List.of(new Point(point.row(), point.column() - 1), targetPoint.move(-1, 1), targetPoint);
        }

        if (rowGap > 0) {
            return List.of(new Point(point.row(), point.column() + 1), targetPoint.move(1, -1), targetPoint);
        }
        return List.of(new Point(point.row(), point.column() + 1), targetPoint.move(-1, -1), targetPoint);
    }

    @Override
    public TeamColor getColor() {
        return this.color;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Sang(color, afterPoint);
    }
}
