package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Position;
import model.Team;
import model.board.Board;

public abstract class Piece {

    protected final List<Route> routes = new ArrayList<>();
    private Team team;
    protected Position position;

    protected Piece(int x, int y, Team team) {
        this.team = team;
        position = new Position(x, y);
    }

    public void move(Board board, Team currentTurn, int dx, int dy) {
        Position target = position.move(dx, dy);
        if (!board.isInboard(target)) {
            throw new IllegalArgumentException();
        }
        if (!currentTurn.equals(team)) {
            throw new IllegalArgumentException();
        }
        Route movableRoute = findMovableRoute(board, dx, dy);
        if (movableRoute == null) {
            throw new IllegalArgumentException();
        }
        validateRoute(board, movableRoute, target);
        arrival(board, target);
        position = target;
    }

    protected Route findMovableRoute(Board board, int dx, int dy) {
        Position target = position.move(dx, dy);
        for (var route : routes) {
            Position routeSum = route.sum();
            Position expected = position.move(routeSum);
            if (target.equals(expected)) {
                return route;
            }
        }
        return null;
    }

    // TODO: 시그니처가 포, 차에 의존적으로 구성됨
    protected void validateRoute(Board board, Route route, Position target) {
        Position onRoute = position;
        for (int i = 0; i < route.positions.size() - 1; i++) {
            onRoute = onRoute.move(route.positions.get(i));
            if (board.hasPieceOn(onRoute)) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void arrival(Board board, Position target) {
        if (!board.hasPieceOn(target)) {
            return;
        }
        Piece targetPiece = board.get(target);
        if (targetPiece.team == team) {
            throw new IllegalArgumentException();
        }
        board.take(targetPiece);
    }

    public Position getPosition() {
        return position;
    }

    public boolean onPosition(Position nextPos) {
        return position.equals(nextPos);
    }

    protected record Route(
        List<Position> positions
    ) {

        public Position sum() {
            int sumX = positions.stream()
                .mapToInt(Position::x)
                .sum();
            int sumY = positions.stream()
                .mapToInt(Position::y)
                .sum();
            return new Position(sumX, sumY);
        }
    }

    public Team getTeam() {
        return team;
    }
}
