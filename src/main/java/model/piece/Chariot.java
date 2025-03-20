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
        Position validatePosition = nextPositionOnRoute(position, route);
        while (!validatePosition.equals(target)) {
            validateOtherPieceOnRoute(board, validatePosition);
            validatePosition = validatePosition.move(route.positions().getFirst());
        }
    }

    private static void validateOtherPieceOnRoute(Board board, Position validatePosition) {
        if (board.hasPieceOn(validatePosition)) {
            throw new IllegalArgumentException("[ERROR] 이동 경로에 다른 기물이 존재합니다.");
        }
    }

    private Position nextPositionOnRoute(Position position, Route route) {
        return position.move(route.positions().getFirst());
    }

    @Override
    public PieceType type() {
        return PieceType.CHARIOT;
    }
}
