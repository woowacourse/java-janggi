package model.piece;

import java.util.List;

import model.Board;
import model.Position;
import model.Team;

public class Pao extends Piece {

    public Pao(int x, int y, Team team) {
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
        boolean flag = false;
        Position target = position.move(dx, dy);
        for (var route : routes) {
            Position dir = route.positions().getFirst();
            Position nextPos = position.move(dir.x(), dir.y());
            while (board.isInboard(nextPos)) {
                if(board.hasPieceOn(nextPos)){
                    flag = true;
                }
                if (nextPos.equals(target) && flag)  {
                    return route;
                }
                nextPos = nextPos.move(dir.x(), dir.y());
            }
        }
        return null;
    }

    @Override
    protected void validateRoute(Board board, Route route) {
        boolean flag = false;
        Position onRoute = position.move(route.positions().getFirst());
        for (; board.isInboard(onRoute); onRoute = onRoute.move(route.positions().getFirst())) {
            if (board.hasPieceOn(onRoute)) {
                if (board.get(onRoute) instanceof Pao) {
                    throw new IllegalArgumentException();
                }
                if (flag) {
                    throw new IllegalArgumentException();
                }
                flag = true;
            }
        }
    }
}
