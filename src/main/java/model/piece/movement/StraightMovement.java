package model.piece.movement;

import java.util.List;

import model.Position;
import model.Team;
import model.board.BoardSearcher;
import model.piece.Piece;

public class StraightMovement implements Movement {

    @Override
    public Piece.Route findMovableRoute(BoardSearcher boardSearcher, List<Piece.Route> routes, Team team,
        Position position, Position difference) {
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
    public void validateRoute(BoardSearcher boardSearcher, Piece.Route route, Position position, Position difference) {
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

    private Position nextPositionOnRoute(Position position, Piece.Route route) {
        return position.move(route.positions().getFirst());
    }
}
