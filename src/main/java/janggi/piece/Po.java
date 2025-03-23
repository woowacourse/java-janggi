package janggi.piece;

import janggi.game.Board;
import janggi.point.Direction;
import janggi.point.InitialPoint;
import janggi.point.Point;
import janggi.game.Team;
import janggi.point.PointDistance;
import janggi.point.Route;
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
        // 포를 공격하려고 하면 false
        if (board.hasPieceOnPoint(targetPoint)
                && board.findByPoint(targetPoint) instanceof Po
        ) {
            return false;
        }
        //목표지점까지의 루트 구함
        Direction direction = Direction.cardinalFrom(this.point, targetPoint);
        Route route = Route.repeat(direction, this.point, targetPoint);

        List<Point> crashPoints = route.findCrashes(board);
        if (crashPoints.isEmpty() || crashPoints.size() > 2) {
            return false; //장애물 있으면 false
        }

        //bridge 확인
        Point bridgePoint = crashPoints.getFirst();
        if (board.findByPoint(bridgePoint) instanceof Po) {
            return false;
        }
        if (bridgePoint.equals(targetPoint)) {
            return false;
        }

        //prey 확인
        if (crashPoints.size() == 2) {
            Point preyPoint = crashPoints.getLast();
            Movable prey = board.findByPoint(preyPoint);
            if (prey instanceof Po) {
                return false;
            }
            if (!preyPoint.equals(targetPoint)) {
                return false;
            }
        }
        return true;
    }

//    private boolean findHurdle(Point current, List<Point> hurdles, Board board) {
//        if (board.hasPieceOnPoint(current)) {
//            Movable piece = board.findByPoint(current);
//            if (piece instanceof Po && hurdles.isEmpty()) {
//                return false;
//            }
//            if (!hurdles.isEmpty()) {
//                return false;
//            }
//            hurdles.add(current);
//            return true;
//        }
//        return true;
//    }

    @Override
    public boolean isInMovingRange(Point targetPoint) {
        return point.isSameRow(targetPoint) || point.isSameColumn(targetPoint);
    }

    @Override
    public Route findRoute(Point targetPoint) {
        //TODO 현재 사용중이지 않음
        return null;
    }

    @Override
    public Movable updatePoint(Point afterPoint) {
        return new Po(team, afterPoint);
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
