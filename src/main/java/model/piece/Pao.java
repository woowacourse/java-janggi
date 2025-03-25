package model.piece;

import java.util.List;

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
    protected Route findMovableRoute(BoardSearcher boardSearcher, Position difference) {
        Position target = position.move(difference);
        if (isTargetPao(boardSearcher, target)) {
            throw new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다.");
        }
        return movableRoute(boardSearcher, target);
    }

    private Route movableRoute(BoardSearcher boardSearcher, Position target) {
        return routes.stream()
            .filter(route -> validateRoute(boardSearcher, target, route))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 도달할 수 없는 위치입니다."));
    }

    private boolean validateRoute(BoardSearcher boardSearcher, Position target, Route route) {
        boolean isOvered = false;
        Position nextPos = nextPositionOnRoute(position, route);
        while (boardSearcher.isInBoard(nextPos)) {
            if (overPiece(boardSearcher, nextPos)) {
                isOvered = true;
            }
            if (nextPos.equals(target) && isOvered) {
                return true;
            }
            nextPos = nextPositionOnRoute(nextPos, route);
        }
        return false;
    }

    private boolean isTargetPao(BoardSearcher boardSearcher, Position target) {
        return boardSearcher.hasPieceOn(target) && boardSearcher.get(target).type() == PieceType.PAO;
    }

    private boolean overPiece(BoardSearcher boardSearcher, Position nextPos) {
        return boardSearcher.hasPieceOn(nextPos);
    }

    @Override
    protected void validateRoute(BoardSearcher boardSearcher, Route route, Position difference) {
        boolean isOvered = false;
        Position targetPosition = position.move(difference);
        Position validatePosition = nextPositionOnRoute(position, route);
        while (!validatePosition.equals(targetPosition)) {
            if (boardSearcher.hasPieceOn(validatePosition)) {
                validateOverPao(boardSearcher, validatePosition);
                validateIsOvered(isOvered);
                isOvered = true;
            }
            validatePosition = nextPositionOnRoute(validatePosition, route);
        }
    }

    private void validateOverPao(BoardSearcher boardSearcher, Position validatePosition) {
        if (boardSearcher.get(validatePosition).type() == PieceType.PAO) {
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
