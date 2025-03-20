package model.piece;

import java.util.List;

import model.Position;
import model.Team;
import model.board.Board;

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
        if (board.hasPieceOn(target) && board.get(target).type() == PieceType.PAO) {
            return null;
        }
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
    protected void validateRoute(Board board, Route route, Position target) {
        boolean flag = false;
        Position onRoute = position.move(route.positions().getFirst());
        for (; !onRoute.equals(target); onRoute = onRoute.move(route.positions().getFirst())) {
            if (board.hasPieceOn(onRoute)) {
                if (board.get(onRoute).type() == PieceType.PAO) {
                    throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
                }
                if (flag) {
                    throw new IllegalArgumentException("[ERROR] 포는 기물을 1개만 넘을 수 있습니다.");
                }
                flag = true;
            }
        }
    }

    @Override
    public PieceType type() {
        return PieceType.PAO;
    }
}
