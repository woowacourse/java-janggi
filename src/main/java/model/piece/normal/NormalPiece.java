package model.piece.normal;

import model.Position;
import model.Team;
import model.piece.BoardSearcher;
import model.piece.Piece;
import model.piece.PieceType;

public abstract class NormalPiece extends Piece {

    protected NormalPiece(int x, int y, Team team) {
        super(x, y, team);
    }

    public abstract PieceType type();

    protected Piece.Route findMovableRoute(BoardSearcher boardSearcher, Position difference) {
        Position target = position.move(difference);
        for (var route : routes) {
            Position routeSum = route.sum();
            Position expected = position.move(routeSum);
            if (target.equals(expected)) {
                return route;
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    protected void validateRoute(BoardSearcher boardSearcher, Piece.Route route, Position difference) {
        Position onRoute = position;
        for (int i = 0; i < route.positions().size() - 1; i++) {
            onRoute = onRoute.move(route.positions().get(i));
            if (boardSearcher.hasPieceOn(onRoute)) {
                throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
            }
        }
    }
}
