package janggi.domain.piece;

import janggi.domain.board.BoardInterface;
import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.policy.RoutePolicy;

import java.util.ArrayList;
import java.util.List;

public abstract class StepPiece extends ActivePiece {
    private final List<List<Movement>> moveRange;

    public StepPiece(List<List<Movement>> moveRange, RoutePolicy routePolicy, Side side, PieceType pieceType) {
        super(routePolicy, side, pieceType);
        this.moveRange = moveRange;
    }

    @Override
    public List<Position> findRoute(Position start, Position end) {
        return moveRange.stream()
                .map(movements -> calculatePath(start, movements))
                .filter(path -> path.getLast().equals(end))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 도착 지점이 아닙니다."));
    }

    @Override
    public void validateRoute(List<Position> path, BoardInterface boardInterface) {
        if(!routePolicy.isMovable(path, side, boardInterface)) {
            throw new IllegalArgumentException("이동할 수 없는 경로입니다.");
        }
    }

    private List<Position> calculatePath(Position start, List<Movement> path) {
        List<Position> calculatedPath = new ArrayList<>(List.of(start));
        for (Movement movement : path) {
            Position step = calculatedPath.getLast().move(movement);
            calculatedPath.add(step);
        }
        return calculatedPath;
    }
}
