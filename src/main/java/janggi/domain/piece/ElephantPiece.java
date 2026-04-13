package janggi.domain.piece;

import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElephantPiece extends Piece {
    private static final List<List<RelativePosition>> POSSIBLE_RELATIVE_POSITIONS = List.of(
            List.of(new RelativePosition(1, 0), new RelativePosition(2, 1), new RelativePosition(3, 2)),
            List.of(new RelativePosition(1, 0), new RelativePosition(2, -1), new RelativePosition(3, -2)),
            List.of(new RelativePosition(-1, 0), new RelativePosition(-2, 1), new RelativePosition(-3, 2)),
            List.of(new RelativePosition(-1, 0), new RelativePosition(-2, -1), new RelativePosition(-3, -2)),
            List.of(new RelativePosition(0, 1), new RelativePosition(1, 2), new RelativePosition(2, 3)),
            List.of(new RelativePosition(0, 1), new RelativePosition(-1, 2), new RelativePosition(-2, 3)),
            List.of(new RelativePosition(0, -1), new RelativePosition(1, -2), new RelativePosition(2, -3)),
            List.of(new RelativePosition(0, -1), new RelativePosition(-1, -2), new RelativePosition(-2, -3))
    );

    public ElephantPiece(Team team) {
        super(team, Name.ELEPHANT);
    }

    @Override
    public boolean canMoveByBasicMovingRule(Position from, Position to) {
        return POSSIBLE_RELATIVE_POSITIONS.stream()
                .map(List::getLast)
                .anyMatch(relativePosition -> isSamePosition(from, to, relativePosition));
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return POSSIBLE_RELATIVE_POSITIONS.stream()
                .map(relativePositions -> createPath(from, relativePositions))
                .filter(positions -> positions.getLast().equals(to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상의 이동 경로를 생성할 수 없습니다."));
    }

    @Override
    public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 2) {
            return false;
        }
        for (Position position : positionPieces.keySet()) {
            if (position.equals(to)) {
                return !isSameTeam(positionPieces.get(position));
            }
            return false;
        }
        return true;
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

    private record RelativePosition(int dx, int dy) {
    }
}
