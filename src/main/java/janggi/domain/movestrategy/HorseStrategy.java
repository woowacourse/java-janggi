package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {
    private static final List<List<RelativePosition>> POSSIBLE_RELATIVE_POSITIONS = List.of(
            List.of(new RelativePosition(1, 0), new RelativePosition(2, 1)),
            List.of(new RelativePosition(1, 0), new RelativePosition(2, -1)),
            List.of(new RelativePosition(-1, 0), new RelativePosition(-2, 1)),
            List.of(new RelativePosition(-1, 0), new RelativePosition(-2, -1)),
            List.of(new RelativePosition(0, 1), new RelativePosition(1, 2)),
            List.of(new RelativePosition(0, 1), new RelativePosition(-1, 2)),
            List.of(new RelativePosition(0, -1), new RelativePosition(1, -2)),
            List.of(new RelativePosition(0, -1), new RelativePosition(-1, -2))
    );

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return POSSIBLE_RELATIVE_POSITIONS.stream()
                .map(relativePositions -> relativePositions.getLast())
                .anyMatch(relativePosition -> isSamePosition(from, to, relativePosition));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return POSSIBLE_RELATIVE_POSITIONS.stream()
                .map(relativePositions -> createPath(from, relativePositions))
                .filter(positions -> positions.getLast().equals(to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("마의 이동 경로를 생성할 수 없습니다."));
    }

    private boolean isSamePosition(Position from, Position to, RelativePosition relativePosition) {
        return from.deltaX(to) == relativePosition.dx()
                && from.deltaY(to) == relativePosition.dy();
    }

    private List<Position> createPath(Position from, List<RelativePosition> relativePositions) {
        List<Position> path = new ArrayList<>();
        for (RelativePosition relativePosition : relativePositions) {
            path.add(new Position(from.x() + relativePosition.dx(), from.y() + relativePosition.dy()));
        }
        return path;
    }
}
