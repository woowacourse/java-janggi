package janggi.piece;

import janggi.Team;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

import janggi.position.Position;

public abstract class Piece {

    private Team team;
    protected Position position;
    protected final List<Route> routes = new ArrayList<>();

    protected Piece(Position position, Team team) {
        this.team = team;
        this.position = position;
    }

//    public void move(Board board, Team currentTurn, int dx, int dy) {
//        Position target = position.move(dx, dy);
//        if (!board.isInboard(target)) {
//            throw new IllegalArgumentException("[ERROR] 장기판 내에서만 이동할 수 있습니다.");
//        }
//        if (!currentTurn.equals(team)) {
//            throw new IllegalArgumentException("[ERROR] 다른 팀의 기물은 움직일 수 없습니다.");
//        }
//        Route movableRoute = findMovableRoute(board, dx, dy);
//        if (movableRoute == null) {
//            throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
//        }
//        validateRoute(board, movableRoute, target);
//        arrival(board, target);
//        position = target;
//    }
//
//    protected Route findMovableRoute(Board board, int dx, int dy) {
//        Position target = position.move(dx, dy);
//        for (var route : routes) {
//            Position routeSum = route.sum();
//            Position expected = position.move(routeSum);
//            if (target.equals(expected)) {
//                return route;
//            }
//        }
//        return null;
//    }
//
//    protected void validateRoute(Board board, Route route, Position target) {
//        Position onRoute = position;
//        for (int i = 0; i < route.positions.size() - 1; i++) {
//            onRoute = onRoute.move(route.positions.get(i));
//            if (board.hasPieceOn(onRoute)) {
//                throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
//            }
//        }
//    }

//    private void arrival(Board board, Position target) {
//        if (!board.hasPieceOn(target)) {
//            return;
//        }
//        Piece targetPiece = board.get(target);
//        if (targetPiece.team == team) {
//            throw new IllegalArgumentException("[ERROR] 도착 지점에 같은 팀의 기물이 존재합니다.");
//        }
//        board.take(targetPiece);
//    }

    public Position position() {
        return position;
    }

    public boolean onPosition(Position nextPos) {
        return position.equals(nextPos);
    }

    public Team getTeam() {
        return team;
    }

    public abstract PieceType type();
}
