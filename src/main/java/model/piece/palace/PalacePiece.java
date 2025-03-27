package model.piece.palace;

import model.Position;
import model.Team;
import model.board.Palace;
import model.piece.BoardSearcher;
import model.piece.Piece;
import model.piece.PieceType;

public abstract class PalacePiece extends Piece {

    private final Palace palace = new Palace();

    protected PalacePiece(int x, int y, Team team) {
        super(x, y, team);
    }

    public abstract PieceType type();

    protected Route findMovableRoute(BoardSearcher boardSearcher, Position difference) {
        Position target = position.move(difference);
        Route movableRoute = palace.findMovableRouteInPalace(team, position, target);
        if (movableRoute != null) {
            return movableRoute;
        }
        return findMovableRouteInNormalRoute(target);
    }

    private Piece.Route findMovableRouteInNormalRoute(Position target) {
        validatePalaceArea(target);
        for (var route : routes) {
            Position routeSum = route.sum();
            Position expected = position.move(routeSum);
            if (target.equals(expected)) {
                return route;
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    private void validatePalaceArea(Position target) {
        if (!palace.isInPalace(team, target)) {
            throw new IllegalArgumentException("[ERROR] 궁 밖으로 나갈 수 없는 기물입니다.");
        }
    }

    protected void validateRoute(BoardSearcher boardSearcher, Route route, Position difference) {
        Position onRoute = position;
        for (int i = 0; i < route.positions().size() - 1; i++) {
            onRoute = onRoute.move(route.positions().get(i));
            if (boardSearcher.hasPieceOn(onRoute)) {
                throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
            }
        }
    }
}
