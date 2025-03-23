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
        Position target = position.move(dx, dy);
        if (isTargetPao(board, target)) {
            throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
        }
        return movableRoute(board, target);
    }

    private Route movableRoute(Board board, Position target) {
        for (var route : routes) {
            Route route1 = validateRoute(board, target, route);
            if (route1 != null) {
                return route1;
            }
        }
        throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
    }

    private Route validateRoute(Board board, Position target, Route route) {
        boolean isOvered = false;
        Position nextPos = nextPositionOnRoute(position, route);
        while (board.isInBoard(nextPos)) {
            if (overPiece(board, nextPos)) {
                isOvered = true;
            }
            if (nextPos.equals(target) && isOvered) {
                return route;
            }
            nextPos = nextPositionOnRoute(nextPos, route);
        }
        return null;
    }

    private boolean isTargetPao(Board board, Position target) {
        return board.hasPieceOn(target) && board.get(target).type() == PieceType.PAO;
    }

    private boolean overPiece(Board board, Position nextPos) {
        return board.hasPieceOn(nextPos);
    }

    @Override
    protected void validateRoute(Board board, Route route, Position target) {
        boolean isOvered = false;
        Position validatePosition = nextPositionOnRoute(position, route);
        while (!validatePosition.equals(target)) {
            if (board.hasPieceOn(validatePosition)) {
                validateOverPao(board, validatePosition);
                validateIsOvered(isOvered);
                isOvered = true;
            }
            validatePosition = nextPositionOnRoute(validatePosition, route);
        }
    }

    private void validateOverPao(Board board, Position validatePosition) {
        if (board.get(validatePosition).type() == PieceType.PAO) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
        }
    }

    private void validateIsOvered(boolean isOvered) {
        if (isOvered) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 1개만 넘을 수 있습니다.");
        }
    }

    private Position nextPositionOnRoute(Position position, Route route) {
        return position.move(route.positions().getFirst());
    }

    @Override
    public PieceType type() {
        return PieceType.PAO;
    }
}
