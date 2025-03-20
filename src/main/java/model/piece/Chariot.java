package model.piece;

import java.util.List;

import model.Position;
import model.Team;
import model.board.Board;

public class Chariot extends Piece {

    public Chariot(int x, int y, Team team) {
        super(x, y, team);
        routes.addAll(List.of(
            new Route(List.of(new Position(-1, 0))),
            new Route(List.of(new Position(0, 1))),
            new Route(List.of(new Position(1, 0))),
            new Route(List.of(new Position(0, -1)))
        ));
    }

    @Override
    protected Route findMovableRoute(Board board, int dx, int dy) {
        Position target = position.move(dx, dy);
        for (var route : routes) {
            Position dir = route.positions().getFirst();
            Position nextPos = position.move(dir.x(), dir.y());
            while (board.isInboard(nextPos)) {
                if (nextPos.equals(target)) {
                    return route;
                }
                nextPos = nextPos.move(dir.x(), dir.y());
            }
        }
        return null;
    }

    @Override
    protected void validateRoute(Board board, Route route, Position target) {
        Position onRoute = position.move(route.positions().getFirst());
        for (; onRoute.equals(target); onRoute = onRoute.move(route.positions().getFirst())) {
            if (board.hasPieceOn(onRoute)) {
                throw new IllegalArgumentException();
            }
        }
    }
}
