package janggi.piece;

import janggi.Board;
import janggi.point.InitialPoint;
import janggi.Movable;
import janggi.point.Point;
import janggi.Team;
import java.util.ArrayList;
import java.util.List;

public class Po implements Movable {

    private static final String NAME = "포";
    private static final List<Po> pos;

    private final Team team;
    private final Point point;

    public Po(Team team, Point point) {
        this.team = team;
        this.point = point;
    }

    static {
        List<Po> createdPieces = new ArrayList<>();
        for (Point point : InitialPoint.PO.getRedPoints()) {
            createdPieces.add(new Po(Team.HAN, point));
        }
        for (Point point : InitialPoint.PO.getBluePoints()) {
            createdPieces.add(new Po(Team.CHO, point));
        }
        pos = createdPieces;
    }
    public static List<Po> values() {
        return new ArrayList<>(pos);
    }

    public boolean isMovable(Point targetPoint, Board board) {
        if (board.hasPieceOnPoint(targetPoint) && board.findByPoint(targetPoint) instanceof Po) {
            return false;
        }
        List<Point> route = findRoute(targetPoint);
        List<Point> hurdles = new ArrayList<>();
        for (Point point : route) {
            if (findHurdle(point, hurdles, board)) {
                continue;
            }
            return false;
        }
        return hurdles.size() == 1;
    }

    private boolean findHurdle(Point current, List<Point> hurdles, Board board) {
        if (board.hasPieceOnPoint(current)) {
            Movable piece = board.findByPoint(current);
            if (piece instanceof Po && hurdles.isEmpty()) {
                return false;
            }
            if (!hurdles.isEmpty()) {
                return false;
            }
            hurdles.add(current);
            return true;
        }
        return true;
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
    public boolean isInMovingRange(Point targetPoint) {
        return point.isSameRow(targetPoint) || point.isSameColumn(targetPoint);
    }

    @Override
    public List<Point> findRoute(Point targetPoint) {
        List<Point> route = new ArrayList<>();
        if (point.isSameRow(targetPoint)) {
            if (point.isColumnBiggerThan(targetPoint)) {
                for (int column = point.column() - 1; column > targetPoint.column(); column--) {
                    route.add(new Point(point.row(), column));
                }
            }
            if (point.isColumnLessThan(targetPoint)) {
                for (int column = point.column() + 1; column < targetPoint.column(); column++) {
                    route.add(new Point(point.row(), column));
                }
            }

        }
        if (point.isSameColumn(targetPoint)) {
            if (point.isRowBiggerThan(targetPoint)) {
                for (int row = point.row() - 1; row > targetPoint.row(); row--) {
                    route.add(new Point(row, point.column()));
                }
            }
            if (point.isRowLessThan(targetPoint)) {
                for (int row = point.row() + 1; row < targetPoint.row(); row++) {
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
        return new Po(team, afterPoint);
    }
}
