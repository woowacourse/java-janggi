package model.piece.normal;

import model.Position;
import model.Team;
import model.board.Board;
import model.piece.Piece;
import model.piece.PieceType;

public abstract class NormalPiece extends Piece {

    protected NormalPiece(int x, int y, Team team) {
        super(x, y, team);
    }

    protected Piece.Route findMovableRoute(Board board, int dx, int dy) {
        Position target = position.move(dx, dy);
        for (var route : routes) {
            Position routeSum = route.sum();
            Position expected = position.move(routeSum);
            if (target.equals(expected)) {
                return route;
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    protected void validateRoute(Board board, Piece.Route route, Position target) {
        Position onRoute = position;
        for (int i = 0; i < route.positions().size() - 1; i++) {
            onRoute = onRoute.move(route.positions().get(i));
            if (board.hasPieceOn(onRoute)) {
                throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
            }
        }
    }

    public abstract PieceType type();
}
