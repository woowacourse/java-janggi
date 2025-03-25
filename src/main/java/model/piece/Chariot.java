package model.piece;

import java.util.List;

import model.Position;
import model.Team;

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
    protected Route findMovableRoute(BoardSearcher boardSearcher, Position difference) {
        Position target = position.move(difference);
        for (var route : routes) {
            Position dir = route.positions().getFirst();
            Position nextPos = position.move(dir);
            while (boardSearcher.isInBoard(nextPos)) {
                if (nextPos.equals(target)) {
                    return route;
                }
                nextPos = nextPos.move(dir);
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    @Override
    protected void validateRoute(BoardSearcher boardSearcher, Route route, Position difference) {
        Position targetPosition = position.move(difference);
        Position validatePosition = nextPositionOnRoute(position, route);
        while (!validatePosition.equals(targetPosition)) {
            validateOtherPieceOnRoute(boardSearcher, validatePosition);
            validatePosition = validatePosition.move(route.positions().getFirst());
        }
    }

    private static void validateOtherPieceOnRoute(BoardSearcher boardSearcher, Position validatePosition) {
        if (boardSearcher.hasPieceOn(validatePosition)) {
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
