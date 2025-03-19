package janggi.piece;

import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class Po implements Movable {

    private static final String NAME = "포";
    private static final List<Po> pos;

    private final TeamColor color;
    private final Point point;

    public Po(TeamColor color, Point point) {
        this.color = color;
        this.point = point;
    }

    static {
        List<Po> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.PO.getRedPoints()) {
            createdPieces.add(new Po(TeamColor.RED, point));
        }
        for (Point point : InitialPoint.PO.getBluePoints()) {
            createdPieces.add(new Po(TeamColor.BLUE, point));
        }
        pos = createdPieces;
    }
    public static List<Po> values() {
        return new ArrayList<>(pos);
    }

    @Override
    public Point getPoint() {
        return point;
    }

    public boolean isMovable(Point targetPoint, List<Movable> horizontalPieces, List<Movable> verticalPieces) {
        if (point.isSameRow(targetPoint)) {
            for (Movable movable : horizontalPieces) {
                if (movable.getPoint().isColumnBetween(this.getPoint(), targetPoint)) {
                    return true;
                }
            }
        }
        if (point.isSameColumn(targetPoint)) {
            for (Movable movable : verticalPieces) {
                if (movable.getPoint().isRowBetween(this.getPoint(), targetPoint)) {
                    return true;
                }
            }
        }
        return false;
    }

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
    public TeamColor getColor() {
        return this.color;
    }
}
